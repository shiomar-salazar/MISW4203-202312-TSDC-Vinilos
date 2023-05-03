package com.example.tsdc_vinilos_equipo6.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.tsdc_vinilos_equipo6.models.Collector
import com.example.tsdc_vinilos_equipo6.network.NetworkServiceAdapter

class CollectorsRepository (val application: Application) {
    suspend fun refreshData(): List<Collector>{
        return NetworkServiceAdapter.getInstance(application).getCollectors()
    }
}