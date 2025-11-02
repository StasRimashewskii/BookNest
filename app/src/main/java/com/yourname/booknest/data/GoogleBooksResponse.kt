package com.example.booknest.data

data class GoogleBooksResponse(
    val items: List<Item>?
)

data class Item(
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val publishedDate: String?,
    val industryIdentifiers: List<IndustryIdentifier>?,
    val categories: List<String>?,
    val imageLinks: ImageLinks?
)

data class IndustryIdentifier(
    val type: String,
    val identifier: String
)

data class ImageLinks(
    val thumbnail: String?
)