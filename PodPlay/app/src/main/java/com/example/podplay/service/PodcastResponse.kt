package com.example.podplay.service

// PodcastResponse json model is placed in service package
// as it is used for handling response from the iTunes Service

data class PodcastResponse(
    val resultCount: Int,
    val results: List<ItunesPodcast>) {

    data class ItunesPodcast(
        val collectionCensoredName: String,
        val feedUrl: String,
        val artworkUrl30: String,
        val releaseDate: String
    )
}