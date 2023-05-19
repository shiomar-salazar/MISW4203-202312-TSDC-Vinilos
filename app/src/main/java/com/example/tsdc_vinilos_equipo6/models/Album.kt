package com.example.tsdc_vinilos_equipo6.models

data class Album(
    val albumId:Int = 0,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String,
    val tracks: List<Track>?,
    val performers: List<Performer>?,
    val comments: List<Comment>?
)
