package com.example.tsdc_vinilos_equipo6.network

import android.content.Context
import com.example.tsdc_vinilos_equipo6.models.Album
import com.example.tsdc_vinilos_equipo6.models.Artist
import com.example.tsdc_vinilos_equipo6.models.Collector
import com.example.tsdc_vinilos_equipo6.models.Comment


class CacheManager(context: Context) {
    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    private var comments: HashMap<Int, List<Comment>> = hashMapOf()
    private var albums: List<Album> = mutableListOf<Album>()
    private var artists: List<Artist> = mutableListOf<Artist>()
    private var artist: List<Artist> = mutableListOf<Artist>()
    private var collectors: List<Collector> = mutableListOf<Collector>()

    fun addComments(albumId: Int, comment: List<Comment>){
        if (!comments.containsKey(albumId)){
            comments[albumId] = comment
        }
    }
    fun getComments(albumId: Int) : List<Comment>{
        return if (comments.containsKey(albumId)) comments[albumId]!! else listOf<Comment>()
    }

    fun addAlbums(newAlbums: List<Album>){
        if (albums.isEmpty()){
            albums = newAlbums
        }
    }
    fun getAlbums() : List<Album>{
        return if (albums.isEmpty()) listOf<Album>() else albums
    }

    fun addArtists(newArtists: List<Artist>){
        if (artists.isEmpty()){
            artists = newArtists
        }
    }

    fun addArtist(newArtists: List<Artist>){
        if (artist.isEmpty()){
            artist = newArtists
        }
    }

    fun getArtists() : List<Artist>{
        return if (artists.isEmpty()) listOf<Artist>() else artists
    }

    fun getArtist():  List<Artist> {
        return if (artist.isEmpty()) listOf<Artist>() else artist
    }

    fun addCollectors(newCollectors: List<Collector>){
        if (collectors.isEmpty()){
            collectors = newCollectors
        }
    }
    fun getCollectors() : List<Collector>{
        return if (collectors.isEmpty()) listOf<Collector>() else collectors
    }
}