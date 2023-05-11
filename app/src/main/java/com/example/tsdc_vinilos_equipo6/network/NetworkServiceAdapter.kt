package com.example.tsdc_vinilos_equipo6.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tsdc_vinilos_equipo6.models.Album
import com.example.tsdc_vinilos_equipo6.models.Artist
import com.example.tsdc_vinilos_equipo6.models.Collector
import com.example.tsdc_vinilos_equipo6.models.Comment
import com.example.tsdc_vinilos_equipo6.models.Performer
import com.example.tsdc_vinilos_equipo6.models.Prize
import com.example.tsdc_vinilos_equipo6.models.Track
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "http://54.172.124.166/"
        private var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun getAlbums() = suspendCoroutine<List<Album>> { cont ->
        requestQueue.add(
            getRequest("albums",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Album>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        val respPerformer = item.getJSONArray("performers")
                        val listPerformers = mutableListOf<Performer>()
                        for (j in 0 until respPerformer.length()) {
                            val itemPerformer = respPerformer.getJSONObject(j)
                            listPerformers.add(
                                j, Performer(
                                    performerId = itemPerformer.getInt("id"),
                                    name = itemPerformer.optString("name"),
                                    image = itemPerformer.optString("image"),
                                    description = itemPerformer.optString("description"),
                                    creationDate = itemPerformer.optString("creationDate")

                                )
                            )
                        }
                        list.add(
                            i,
                            Album(
                                albumId = item.getInt("id"),
                                name = item.optString("name"),
                                cover = item.optString("cover"),
                                recordLabel = item.optString("recordLabel"),
                                releaseDate = item.optString("releaseDate"),
                                genre = item.optString("genre"),
                                description = item.optString("description"),
                                tracks = null,
                                performers = listPerformers,
                                comments = null
                            )
                        )
                    }
                    cont.resume(list)
                },
                {
                    cont.resumeWithException(it)
                    Log.d("", it.message.toString())
                })
        )
    }

    suspend fun getAlbum(albumId: Int) = suspendCoroutine { cont ->
        var album: Album?
        requestQueue.add(
            getRequest("albums/$albumId",
                { response ->
                    Log.d("tagb", response)
                    val resp = JSONObject(response)

                    val commentsList: JSONArray = resp.getJSONArray("comments")
                    val comments = mutableListOf<Comment>()
                    var comment: JSONObject?

                    for (i in 0 until commentsList.length()) {
                        comment = commentsList.getJSONObject(i)
                        comments.add(
                            Comment(
                                comment.optString("description"),
                                comment.getInt("rating").toString(), albumId
                            )
                        )
                    }

                    val performers = mutableListOf<Performer>()
                    if (resp.has("performers")) {
                        val performersJsonArray = resp.getJSONArray("performers")
                        for (index in 0 until performersJsonArray.length()) {
                            val perfItem = performersJsonArray.getJSONObject(index)
                            performers.add(
                                Performer(
                                    performerId = perfItem.getInt("id"),
                                    name = perfItem.optString("name"),
                                    image = perfItem.optString("image"),
                                    description = perfItem.optString("description"),
                                    creationDate = perfItem.optString("creationDate")
                                )
                            )
                        }
                    }

                    val tracksList: JSONArray = resp.getJSONArray("tracks")
                    val tracks = mutableListOf<Track>()
                    var track: JSONObject?

                    for (i in 0 until tracksList.length()) {
                        track = tracksList.getJSONObject(i)
                        tracks.add(
                            Track(
                                track.getInt("id"),
                                track.optString("name"),
                                track.optString("duration")
                            )
                        )
                    }

                    album = Album(
                        albumId = resp.getInt("id"),
                        name = resp.optString("name"),
                        cover = resp.optString("cover"),
                        recordLabel = resp.optString("recordLabel"),
                        releaseDate = resp.optString("releaseDate"),
                        genre = resp.optString("genre"),
                        description = resp.optString("description"),
                        comments = comments,
                        performers = performers,
                        tracks = tracks
                    )

                    cont.resume(album!!)
                },
                {
                    cont.resumeWithException(it)
                })
        )
    }

    suspend fun getCollectors() = suspendCoroutine<List<Collector>> { cont ->
        requestQueue.add(
            getRequest("collectors",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Collector>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        val collector = Collector(
                            collectorId = item.getInt("id"),
                            name = item.optString("name"),
                            telephone = item.optString("telephone"),
                            email = item.optString("email")
                        )
                        list.add(i, collector)
                    }
                    cont.resume(list)
                },
                {
                    cont.resumeWithException(it)
                    Log.d("", it.message.toString())
                })
        )
    }

    suspend fun getComments(albumId: Int) = suspendCoroutine<List<Comment>> { cont ->
        requestQueue.add(
            getRequest("albums/$albumId/comments",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Comment>()
                    var item: JSONObject?
                    for (i in 0 until resp.length()) {
                        item = resp.getJSONObject(i)
                        Log.d("Response", item.toString())
                        list.add(
                            i,
                            Comment(
                                albumId = albumId,
                                rating = item.getInt("rating").toString(),
                                description = item.optString("description")
                            )
                        )
                    }
                    cont.resume(list)
                },
                {
                    cont.resumeWithException(it)
                    Log.d("", it.message.toString())
                })
        )
    }

    fun postComment(
        body: JSONObject,
        albumId: Int,
        onComplete: (resp: JSONObject) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(
            postRequest("albums/$albumId/comments",
                body,
                { response ->
                    onComplete(response)
                },
                {
                    onError(it)
                })
        )
    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

    private fun postRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.POST,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }

    private fun putRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.PUT,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }

    suspend fun getArtists() = suspendCoroutine<List<Artist>> { cont ->
        requestQueue.add(
            getRequest("musicians",
                { response ->
                    Log.d("ResponseGelAllArtists", response)
                    val resp = JSONArray(response)
                    val list = mutableListOf<Artist>()
                    val listAlbums = mutableListOf<Album>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        val respAlbums = item.getJSONArray("albums")
                        for (j in 0 until respAlbums.length()) {
                            val itemAlbum = respAlbums.getJSONObject(j)
                            listAlbums.add(
                                j, Album(
                                    albumId = itemAlbum.getInt("id"),
                                    name = itemAlbum.optString("name"),
                                    cover = itemAlbum.optString("cover"),
                                    releaseDate = itemAlbum.optString("releaseDate"),
                                    description = itemAlbum.optString("description"),
                                    genre = itemAlbum.optString("genre"),
                                    recordLabel = itemAlbum.optString("recordLabel"),
                                    tracks = null,
                                    performers = null,
                                    comments = null
                                )
                            )
                        }

                        list.add(
                            i,
                            Artist(
                                artistId = item.getInt("id"),
                                name = item.optString("name"),
                                birthDate = item.optString("birthDate").substring(0, 10),
                                image = item.optString("image"),
                                description = item.optString("description"),
                                albums = listAlbums,
                                prizes = null
                            )
                        )
                    }
                    Log.d("ArtistsList", list.toString())
                    cont.resume(list)
                },
                {
                    cont.resumeWithException(it)
                    Log.d("", it.message.toString())
                })
        )
    }

    suspend fun getArtist(musicianId: Int) = suspendCoroutine { cont ->
        var artist: Artist?
        requestQueue.add(
            getRequest("musicians/$musicianId",
                { response ->
                    Log.d("ResponseGetMusician", response)
                    val resp = JSONObject(response)
                    val listAlbums = mutableListOf<Album>()

                    val respAlbums = resp.getJSONArray("albums")
                    for (j in 0 until respAlbums.length()) {
                        val itemAlbum = respAlbums.getJSONObject(j)
                        listAlbums.add(
                            j, Album(
                                albumId = itemAlbum.getInt("id"),
                                name = itemAlbum.optString("name"),
                                cover = itemAlbum.optString("cover"),
                                releaseDate = itemAlbum.optString("releaseDate"),
                                description = itemAlbum.optString("description"),
                                genre = itemAlbum.optString("genre"),
                                recordLabel = itemAlbum.optString("recordLabel"),
                                tracks = null,
                                performers = null,
                                comments = null
                            )
                        )
                    }
                    val listPrizes = mutableListOf<Prize>()
                    val respPrizes = resp.getJSONArray("performerPrizes")
                    for (k in 0 until respPrizes.length()) {
                        val itemPrize = respPrizes.getJSONObject(k)
                        val prize = itemPrize.getJSONObject("prize")
                        listPrizes.add(
                            k, Prize(
                                prizeId = itemPrize.getInt("id"),
                                name = prize.optString("name"),
                                description = prize.optString("description"),
                                organization = prize.optString("organization"),
                                premiationDate = itemPrize.optString("premiationDate")
                            )
                        )
                    }
                    artist = Artist(
                        artistId = resp.getInt("id"),
                        name = resp.optString("name"),
                        birthDate = resp.optString("birthDate").substring(0, 10),
                        image = resp.optString("image"),
                        description = resp.optString("description"),
                        albums = listAlbums,
                        prizes = listPrizes
                    )
                    cont.resume(artist!!)
                },
                {
                    cont.resumeWithException(it)
                })
        )
    }
}

