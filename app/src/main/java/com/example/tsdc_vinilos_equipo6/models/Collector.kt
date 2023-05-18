package com.example.tsdc_vinilos_equipo6.models

data class Collector (
    val collectorId: Int,
    val name:String,
    val telephone:String,
    val email:String,
    val albums: List<Album>?
)
