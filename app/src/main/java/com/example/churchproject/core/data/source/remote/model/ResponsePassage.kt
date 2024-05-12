package com.example.churchproject.core.data.source.remote.model

data class ResponsePassage(
    val data:List<Passage>
)


data class Passage(
    val no:Int=0,
    val abbr:String="",
    val name:String="",
    val chapter:Int=0
)
