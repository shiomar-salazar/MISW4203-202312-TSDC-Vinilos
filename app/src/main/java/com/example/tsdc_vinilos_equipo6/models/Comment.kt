package com.example.tsdc_vinilos_equipo6.models

data class Comment (
    val id: Int = 0,
    val description:String,
    val rating:String,
    val albumId:Int,
    val collectorID: Int,
)
