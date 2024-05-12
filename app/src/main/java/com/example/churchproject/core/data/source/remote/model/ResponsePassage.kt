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

data class ResponseChapter(
    val data: BibleContent
)

data class BibleContent(
    val book: Book,
    val verses: List<Verse>
)

data class Book(
    val no: Int,
    val name: String,
    val chapter: Int
)

data class Verse(
    val verse: Int,
    val type: String,
    val content: String
)
