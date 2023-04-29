package com.example.tsdc_vinilos_equipo6.models

data class Album(
    val albumId:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String,
    val tracks: List<Track>?,
    val performers: List<Performer>?,
    val performersNames: String?,
    val comments: List<Comment>?
)
