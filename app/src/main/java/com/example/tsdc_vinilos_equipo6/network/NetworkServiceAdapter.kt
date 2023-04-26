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
import org.json.JSONArray
import org.json.JSONObject

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

    fun getAlbums(onComplete: (resp: List<Album>) -> Unit, onError: (error: VolleyError) -> Unit) {
        requestQueue.add(
            getRequest("albums",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Album>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(
                            i,
                            Album(
                                albumId = item.getInt("id"),
                                name = item.getString("name"),
                                cover = item.getString("cover"),
                                recordLabel = item.getString("recordLabel"),
                                releaseDate = item.getString("releaseDate"),
                                genre = item.getString("genre"),
                                description = item.getString("description"),
                                performers = null
                            )
                        )
                    }
                    onComplete(list)
                },
                {
                    onError(it)
                })
        )
    }

    fun getCollectors(
        onComplete: (resp: List<Collector>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(
            getRequest("collectors",
                { response ->
                    Log.d("tagb", response)
                    val resp = JSONArray(response)
                    val list = mutableListOf<Collector>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(
                            i,
                            Collector(
                                collectorId = item.getInt("id"),
                                name = item.getString("name"),
                                telephone = item.getString("telephone"),
                                email = item.getString("email")
                            )
                        )
                    }
                    onComplete(list)
                },
                {
                    onError(it)
                    Log.d("", it.message.toString())
                })
        )
    }

    fun getComments(
        albumId: Int,
        onComplete: (resp: List<Comment>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
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
                                description = item.getString("description")
                            )
                        )
                    }
                    onComplete(list)
                },
                {
                    onError(it)
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

    fun getArtists(
        onComplete: (resp: List<Artist>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
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
                                    name = itemAlbum.getString("name"),
                                    cover = itemAlbum.getString("cover"),
                                    releaseDate = itemAlbum.getString("releaseDate"),
                                    description = itemAlbum.getString("description"),
                                    genre = itemAlbum.getString("genre"),
                                    recordLabel = itemAlbum.getString("recordLabel"),
                                    performers = null
                                )
                            )
                        }

                        list.add(
                            i,
                            Artist(
                                artistId = item.getInt("id"),
                                name = item.getString("name"),
                                birthDate = item.getString("birthDate"),
                                image = item.getString("image"),
                                description = item.getString("description"),
                                albums = listAlbums
                            )
                        )
                    }
                    Log.d("ArtistsList", list.toString())
                    onComplete(list)
                },
                {
                    onError(it)
                    Log.d("", it.message.toString())
                })
        )
    }
}