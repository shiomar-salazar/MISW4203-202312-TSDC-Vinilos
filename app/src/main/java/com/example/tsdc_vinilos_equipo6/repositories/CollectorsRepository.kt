package com.example.tsdc_vinilos_equipo6.repositories

import android.app.Application
import android.util.Log
import com.example.tsdc_vinilos_equipo6.models.Collector
import com.example.tsdc_vinilos_equipo6.network.CacheManager
import com.example.tsdc_vinilos_equipo6.network.NetworkServiceAdapter

class CollectorsRepository(val application: Application) {
    suspend fun refreshCollectorsData(): List<Collector> {
        val potentialResp = CacheManager.getInstance(application.applicationContext).getCollectors()
        return if (potentialResp.isEmpty()) {
            Log.d("Cache decision", "get from network")
            val collector = NetworkServiceAdapter.getInstance(application).getCollectors()
            CacheManager.getInstance(application.applicationContext).addCollectors(collector)
            collector
        } else {
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            potentialResp
        }
    }

    suspend fun refreshCollectorData(collectorId: Int): Collector {
        val potentialResp =
            CacheManager.getInstance(application.applicationContext).getCollector(collectorId)
        return if (potentialResp == null) {
            Log.d("Cache decision", "get from network")
            val collector = NetworkServiceAdapter.getInstance(application).getCollector(collectorId)
            CacheManager.getInstance(application.applicationContext)
                .addCollector(collectorId, collector)
            collector
        } else {
            Log.d("Cache decision", "return ${potentialResp.name} from cache")
            potentialResp
        }
    }
}