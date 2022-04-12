package com.example.cvstest

data class ImageResponse(
    val title: String,
    val link: String,
    val description: String,
    val modified: String,
    val generator: String,
    val items: List<Item>
)

data class Item(
    val title: String,
    val link: String,
    val media: Media,
    val dateTaken: String,
    val description: String,
    val published: String,
    val author: String,
    val authorID: String,
    val tags: String
)

data class Media(
    val m: String
)

