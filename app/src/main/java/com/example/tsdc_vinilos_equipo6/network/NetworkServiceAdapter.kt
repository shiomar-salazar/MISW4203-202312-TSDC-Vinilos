package com.example.tsdc_vinilos_equipo6.network

import android.annotation.SuppressLint
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
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
                    var item: JSONObject
                    var itemPerformer: JSONObject
                    var respPerformer: JSONArray
                    (0 until resp.length()).forEach { it ->
                        val listPerformers = mutableListOf<Performer>()
                        item = resp.getJSONObject(it)
                        respPerformer = item.getJSONArray("performers")
                        (0 until respPerformer.length()).forEach {
                            itemPerformer = respPerformer.getJSONObject(it)
                            listPerformers.add(
                                it, Performer(
                                    performerId = itemPerformer.getInt("id"),
                                    name = itemPerformer.optString("name"),
                                    image = itemPerformer.optString("image"),
                                    description = itemPerformer.optString("description"),
                                    creationDate = itemPerformer.optString("creationDate")

                                )
                            )
                        }
                        list.add(
                            it,
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
                })
        )
    }

    suspend fun getAlbum(albumId: Int) = suspendCoroutine { cont ->
        var album: Album?
        requestQueue.add(
            getRequest("albums/$albumId",
                { response ->
                    val resp = JSONObject(response)
                    val commentsList: JSONArray = resp.getJSONArray("comments")
                    val comments = mutableListOf<Comment>()
                    var comment: JSONObject
                    var track: JSONObject
                    val tracks = mutableListOf<Track>()
                    var perfItem: JSONObject
                    val performersJsonArray: JSONArray
                    val performers = mutableListOf<Performer>()
                    (0 until commentsList.length()).forEach {
                        comment = commentsList.getJSONObject(it)
                        comments.add(
                            Comment(
                                comment.getInt("id"),
                                comment.optString("description"),
                                comment.getInt("rating").toString(), albumId,
                                comment.optInt("collectorID")
                            )
                        )
                    }
                    if (resp.has("performers")) {
                        performersJsonArray = resp.getJSONArray("performers")
                        (0 until performersJsonArray.length()).forEach {
                            perfItem = performersJsonArray.getJSONObject(it)
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
                    (0 until tracksList.length()).forEach {
                        track = tracksList.getJSONObject(it)
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
                    var item: JSONObject
                    var collector: Collector
                    (0 until resp.length()).forEach {
                        item = resp.getJSONObject(it)
                        collector = Collector(
                            collectorId = item.getInt("id"),
                            name = item.optString("name"),
                            telephone = item.optString("telephone"),
                            email = item.optString("email"),
                            albums = null
                        )
                        list.add(it, collector)
                    }
                    cont.resume(list)
                },
                {
                    cont.resumeWithException(it)
                })
        )
    }

    suspend fun getCollector(collectorId: Int) = suspendCoroutine { cont ->
        var collector: Collector?
        requestQueue.add(
            getRequest("collectors/$collectorId/albums",
                { responseCollectors ->
                    if (responseCollectors.length > 2) {
                        val resp = JSONArray(responseCollectors)
                        val listAlbums = mutableListOf<Album>()
                        var itemCollector: JSONObject
                        (0 until resp.length()).forEach {
                            itemCollector = resp.getJSONObject(it)
                            val album = itemCollector.getJSONObject("album")
                            listAlbums.add(
                                it, Album(
                                    albumId = album.getInt("id"),
                                    name = album.optString("name"),
                                    cover = album.optString("cover"),
                                    releaseDate = album.optString("releaseDate"),
                                    description = album.optString("description"),
                                    genre = album.optString("genre"),
                                    recordLabel = album.optString("recordLabel"),
                                    tracks = null,
                                    performers = null,
                                    comments = null
                                )
                            )
                        }
                        itemCollector = resp.getJSONObject(0)
                        val currentCollector = itemCollector.getJSONObject("collector")
                        collector = Collector(
                            collectorId = currentCollector.getInt("id"),
                            name = currentCollector.optString("name"),
                            telephone = currentCollector.optString("telephone"),
                            email = currentCollector.optString("email"),
                            albums = listAlbums
                        )
                        cont.resume(collector!!)
                    } else {
                        requestQueue.add(
                            getRequest("collectors/$collectorId",
                                { responseCollector ->
                                    val resp = JSONObject(responseCollector)
                                    collector = Collector(
                                        collectorId = resp.getInt("id"),
                                        name = resp.optString("name"),
                                        telephone = resp.optString("telephone"),
                                        email = resp.optString("email"),
                                        albums = null
                                    )
                                    cont.resume(collector!!)
                                },
                                {
                                    cont.resumeWithException(it)
                                })
                        )
                    }
                },
                {
                    cont.resumeWithException(it)
                })
        )
    }

    suspend fun getComments(albumId: Int) = suspendCoroutine<List<Comment>> { cont ->
        requestQueue.add(
            getRequest("albums/$albumId/comments",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Comment>()
                    var item: JSONObject
                    (0 until resp.length()).forEach {
                        item = resp.getJSONObject(it)
                        list.add(
                            it,
                            Comment(
                                id = item.getInt("id"),
                                albumId = albumId,
                                rating = item.getInt("rating").toString(),
                                description = item.optString("description"),
                                collectorID = item.optInt("collectorID")
                            )
                        )
                    }
                    cont.resume(list)
                },
                {
                    cont.resumeWithException(it)
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

    @SuppressLint("SuspiciousIndentation")
    suspend fun getArtists() = suspendCoroutine<List<Artist>> { cont ->
        requestQueue.add(
            getRequest("musicians",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Artist>()
                    var item: JSONObject
                    var itemAlbum: JSONObject
                    var respAlbums: JSONArray
                    val listAlbums = mutableListOf<Album>()
                    (0 until resp.length()).forEach { it ->
                        item = resp.getJSONObject(it)
                        respAlbums = item.getJSONArray("albums")
                        (0 until respAlbums.length()).forEach {
                            itemAlbum = respAlbums.getJSONObject(it)
                            listAlbums.add(
                                it, Album(
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
                            it,
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
                    cont.resume(list)
                },
                {
                    cont.resumeWithException(it)
                })
        )
    }

    suspend fun getArtist(musicianId: Int) = suspendCoroutine { cont ->
        var artist: Artist?
        requestQueue.add(
            getRequest("musicians/$musicianId",
                { response ->
                    val resp = JSONObject(response)
                    val listAlbums = mutableListOf<Album>()
                    var itemAlbum: JSONObject
                    val respAlbums = resp.getJSONArray("albums")
                    (0 until respAlbums.length()).forEach {
                        itemAlbum = respAlbums.getJSONObject(it)
                        listAlbums.add(
                            it, Album(
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
                    var itemPrize: JSONObject
                    var prize: JSONObject
                    (0 until respPrizes.length()).forEach {
                        itemPrize = respPrizes.getJSONObject(it)
                        prize = itemPrize.getJSONObject("prize")
                        listPrizes.add(
                            it, Prize(
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

    suspend fun addComment(albumId: Int, comment: Comment) = suspendCoroutine { cont ->
        requestQueue.add(
            postRequest(
                "albums/$albumId/comments",
                JSONObject(
                    """{"description":"${comment.description}",
                    |"rating":${comment.rating.toFloat()},
                    |"collector":{"id":${comment.collectorID}}}""".trimMargin()
                ),
                { response ->
                    val collector = response.getJSONObject("collector")
                    val album = response.getJSONObject("album")
                    val commentCreated = Comment(
                        id = response.optInt("id"),
                        albumId = album.optInt("id"),
                        description = response.optString("description"),
                        rating = response.optString("rating"),
                        collectorID = collector.optInt("id")
                    )

                    cont.resume(commentCreated)
                },
                {
                    cont.resumeWithException(it)
                })
        )
    }

    suspend fun addAlbum(album: Album) = suspendCoroutine { cont ->
        requestQueue.add(
            postRequest(
                "albums",
                JSONObject(
                    """{"name":"${album.name}",
                    |"cover":"${album.cover}",
                    |"releaseDate":"${album.releaseDate}",
                    |"description":"${album.description}",
                    |"genre":"${album.genre}",
                    |"recordLabel":"${album.recordLabel}"}""".trimMargin()
                ),
                { response ->
                    val albumCreated = Album(
                        albumId = response.optInt("albumId"),
                        name = response.optString("name"),
                        cover = response.optString("cover"),
                        releaseDate = response.optString("releaseDate"),
                        description = response.optString("description"),
                        genre = response.optString("genre"),
                        recordLabel = response.optString("recordLabel"),
                        tracks = mutableListOf(),
                        performers = mutableListOf(),
                        comments = mutableListOf()
                    )
                    cont.resume(albumCreated)
                },
                {
                    cont.resumeWithException(it)
                })
        )
    }
}

