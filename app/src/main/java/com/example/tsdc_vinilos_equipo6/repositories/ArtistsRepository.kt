package com.example.tsdc_vinilos_equipo6.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.tsdc_vinilos_equipo6.models.Artist
import com.example.tsdc_vinilos_equipo6.models.Collector
import com.example.tsdc_vinilos_equipo6.network.NetworkServiceAdapter

class ArtistsRepository(val application: Application) {
    suspend fun refreshData(): List<Artist>{
        return NetworkServiceAdapter.getInstance(application).getArtists()
    }
}