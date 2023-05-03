package com.example.tsdc_vinilos_equipo6.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.tsdc_vinilos_equipo6.models.Comment
import com.example.tsdc_vinilos_equipo6.network.NetworkServiceAdapter

class CommentsRepository (val application: Application){
    suspend fun refreshData(albumId: Int): List<Comment> {
        return NetworkServiceAdapter.getInstance(application).getComments(albumId)
    }
}