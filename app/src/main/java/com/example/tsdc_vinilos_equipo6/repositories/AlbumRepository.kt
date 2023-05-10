package com.example.tsdc_vinilos_equipo6.repositories

import android.app.Application
import android.util.Log
import com.example.tsdc_vinilos_equipo6.models.Album
import com.example.tsdc_vinilos_equipo6.network.CacheManager
import com.example.tsdc_vinilos_equipo6.network.NetworkServiceAdapter

class AlbumRepository(val application: Application) {
    suspend fun refreshAlbumsData(): List<Album> {

        val potentialResp = CacheManager.getInstance(application.applicationContext).getAlbums()
        return if (potentialResp.isEmpty()) {
            Log.d("Cache decision", "get from network")
            val albums = NetworkServiceAdapter.getInstance(application).getAlbums()
            CacheManager.getInstance(application.applicationContext).addAlbums(albums)
            albums
        } else {
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            potentialResp
        }
    }

    suspend fun refreshAlbumData(albumId: Int): Album {
        val potentialResp =
            CacheManager.getInstance(application.applicationContext).getAlbum(albumId)
        return if (potentialResp == null) {
            Log.d("Cache decision", "get from network")
            val album = NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
            CacheManager.getInstance(application.applicationContext).addAlbum(albumId, album)
            album
        } else {
            Log.d("Cache decision", "return ${potentialResp.name} from cache")
            potentialResp
        }
    }
}