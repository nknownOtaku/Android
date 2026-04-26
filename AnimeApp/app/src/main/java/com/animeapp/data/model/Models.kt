package com.animeapp.data.model

import com.google.gson.annotations.SerializedName

data class Anime(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: Title,
    @SerializedName("synopsis") val synopsis: String?,
    @SerializedName("coverImage") val coverImage: CoverImage,
    @SerializedName("format") val format: String?,
    @SerializedName("episodes") val episodes: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("seasonYear") val seasonYear: Int?,
    @SerializedName("genres") val genres: List<String>?,
    @SerializedName("averageScore") val averageScore: Int?,
    @SerializedName("popularity") val popularity: Int?
)

data class Title(
    @SerializedName("romaji") val romaji: String?,
    @SerializedName("english") val english: String?,
    @SerializedName("native") val native: String?
)

data class CoverImage(
    @SerializedName("large") val large: String?,
    @SerializedName("medium") val medium: String?,
    @SerializedName("small") val small: String?
)

data class AnimeResponse(
    @SerializedName("data") val data: Data?,
    @SerializedName("errors") val errors: List<Error>?
)

data class Data(
    @SerializedName("Page") val page: Page?
)

data class Page(
    @SerializedName("media") val media: List<Anime>?,
    @SerializedName("pageInfo") val pageInfo: PageInfo?
)

data class PageInfo(
    @SerializedName("total") val total: Int?,
    @SerializedName("perPage") val perPage: Int?,
    @SerializedName("currentPage") val currentPage: Int?,
    @SerializedName("lastPage") val lastPage: Int?,
    @SerializedName("hasNextPage") val hasNextPage: Boolean?
)

data class Error(
    @SerializedName("message") val message: String?
)

// News Article Model
data class NewsArticle(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("excerpt") val excerpt: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("url") val url: String
)

// Character Model
data class Character(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val CharacterName,
    @SerializedName("image") val CharacterImage,
    @SerializedName("description") val description: String?
)

data class CharacterName(
    @SerializedName("full") val full: String?,
    @SerializedName("native") val native: String?
)

data class CharacterImage(
    @SerializedName("large") val large: String?,
    @SerializedName("medium") val medium: String?
)
