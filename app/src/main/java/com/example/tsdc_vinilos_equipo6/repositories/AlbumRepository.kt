package com.example.tsdc_vinilos_equipo6.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.tsdc_vinilos_equipo6.models.Album
import com.example.tsdc_vinilos_equipo6.network.NetworkServiceAdapter

class AlbumRepository(val application: Application) {
    fun refreshData(callback: (List<Album>) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbums(
            {
                callback(it)
            },
            onError
        )
    }
}