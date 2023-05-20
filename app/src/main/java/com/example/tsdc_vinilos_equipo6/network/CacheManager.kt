package com.example.tsdc_vinilos_equipo6.network

import android.content.Context
import com.example.tsdc_vinilos_equipo6.models.Album
import com.example.tsdc_vinilos_equipo6.models.Artist
import com.example.tsdc_vinilos_equipo6.models.Collector
import com.example.tsdc_vinilos_equipo6.models.Comment

class CacheManager(context: Context) {
    companion object {
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    private var comments: HashMap<Int, List<Comment>> = hashMapOf()
    private var album: HashMap<Int, Album> = hashMapOf()
    private var albums: List<Album> = mutableListOf()
    private var artists: List<Artist> = mutableListOf<Artist>()
    private var artist: HashMap<Int, Artist> = hashMapOf()
    private var collectors: List<Collector> = mutableListOf<Collector>()
    private var collector: HashMap<Int, Collector> = hashMapOf()

    fun addComments(albumId: Int, comment: List<Comment>) {
        if (!comments.containsKey(albumId)) {
            comments[albumId] = comment
        }
    }

    fun addComment(albumId: Int, albumComment: Comment) {
        var listComments = album[albumId]?.comments
        listComments = listComments?.plus(albumComment)
        album[albumId]?.comments = listComments
    }

    fun getComments(albumId: Int): List<Comment> {
        return if (comments.containsKey(albumId)) comments[albumId]!! else listOf<Comment>()
    }

    /* Start Album Cache Logic */
    fun addAlbum(albumId: Int, newAlbum: Album) {
        if (!album.containsKey(albumId)) {
            album[albumId] = newAlbum
        }
        albums += newAlbum
    }

    fun getAlbum(albumId: Int): Album? {
        return if (album[albumId] != null) album[albumId] else null
    }

    fun addAlbums(newalbums: List<Album>) {
        albums = newalbums
    }

    fun getAlbums(): List<Album> {
        return if (albums.isNotEmpty()) albums else mutableListOf()
    }
    /* End Album Cache Logic */


    fun addArtists(newArtists: List<Artist>) {
        if (artists.isEmpty()) {
            artists = newArtists
        }
    }

    fun addArtist(artistId: Int, artists: Artist) {
        if (!artist.containsKey(artistId)) {
            artist[artistId] = artists
        }
    }

    fun getArtists(): List<Artist> {
        return if (artists.isEmpty()) listOf<Artist>() else artists
    }

    fun getArtist(artistId: Int): Artist? {
        return artist[artistId]
    }

    fun addCollectors(newCollectors: List<Collector>) {
        if (collectors.isEmpty()) {
            collectors = newCollectors
        }
    }

    fun addCollector(collectorId: Int, collectors: Collector) {
        if (!collector.containsKey(collectorId)) {
            collector[collectorId] = collectors
        }
    }

    fun getCollectors(): List<Collector> {
        return collectors.ifEmpty { listOf<Collector>() }
    }

    fun getCollector(collectorId: Int): Collector? {
        return collector[collectorId]
    }
}