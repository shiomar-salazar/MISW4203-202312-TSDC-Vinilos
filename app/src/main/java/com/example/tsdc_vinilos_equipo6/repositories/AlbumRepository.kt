package com.example.tsdc_vinilos_equipo6.repositories

import android.app.Application
import android.util.Log
import com.example.tsdc_vinilos_equipo6.models.Album
import com.example.tsdc_vinilos_equipo6.network.CacheManager
import com.example.tsdc_vinilos_equipo6.network.NetworkServiceAdapter

class AlbumRepository (val application: Application){
    suspend fun refreshData(): List<Album> {

        val potentialResp = CacheManager.getInstance(application.applicationContext).getAlbums()
        return if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get from network")
            val albums = NetworkServiceAdapter.getInstance(application).getAlbums()
            CacheManager.getInstance(application.applicationContext).addAlbums(albums)
            albums
        } else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            potentialResp
        }
    }
}