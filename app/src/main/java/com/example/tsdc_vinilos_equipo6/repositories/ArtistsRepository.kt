package com.example.tsdc_vinilos_equipo6.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.tsdc_vinilos_equipo6.models.Artist
import com.example.tsdc_vinilos_equipo6.network.NetworkServiceAdapter

class ArtistsRepository(val application: Application) {
    fun refreshData(callback: (List<Artist>) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getArtists(
            {
                callback(it)
            },
            onError
        )
    }
}