package com.example.tsdc_vinilos_equipo6.repositories

import android.app.Application
import android.util.Log
import com.example.tsdc_vinilos_equipo6.models.Comment
import com.example.tsdc_vinilos_equipo6.network.CacheManager
import com.example.tsdc_vinilos_equipo6.network.NetworkServiceAdapter

class CommentsRepository(val application: Application) {
    suspend fun refreshCommentsData(albumId: Int): List<Comment> {
        val potentialResp =
            CacheManager.getInstance(application.applicationContext).getComments(albumId)
        if (potentialResp.isEmpty()) {
        return if (potentialResp.isEmpty()) {
            Log.d("Cache decision", "get from network")
            val comments = NetworkServiceAdapter.getInstance(application).getComments(albumId)
            CacheManager.getInstance(application.applicationContext).addComments(albumId, comments)
            comments
        } else {
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            potentialResp
        }
    }

    suspend fun addComment(albumId: Int, comment: Comment): Comment {
        CacheManager.getInstance(application.applicationContext).addComment(albumId, comment)
        return NetworkServiceAdapter.getInstance(application).addComment(albumId, comment)
    }
}