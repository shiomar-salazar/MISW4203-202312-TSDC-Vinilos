package com.example.tsdc_vinilos_equipo6.repositories

import android.app.Application
import android.util.Log
import com.example.tsdc_vinilos_equipo6.models.Artist
import com.example.tsdc_vinilos_equipo6.network.CacheManager
import com.example.tsdc_vinilos_equipo6.network.NetworkServiceAdapter

class ArtistsRepository(val application: Application) {
    suspend fun refreshData(): List<Artist>{

        val potentialResp = CacheManager.getInstance(application.applicationContext).getArtists()
        return if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get from network")
            val artists = NetworkServiceAdapter.getInstance(application).getArtists()
            CacheManager.getInstance(application.applicationContext).addArtists(artists)
            artists
        } else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            potentialResp
        }
    }
}