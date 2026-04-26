package com.animeapp.data.api

import com.animeapp.data.model.AnimeResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Body

interface AnimeApiService {
    
    @POST("https://graphql.anilist.co")
    suspend fun getTopAnime(
        @Body query: QueryRequest
    ): Response<AnimeResponse>
    
    @POST("https://graphql.anilist.co")
    suspend fun searchAnime(
        @Body query: QueryRequest
    ): Response<AnimeResponse>
    
    @POST("https://graphql.anilist.co")
    suspend fun getAnimeDetails(
        @Body query: QueryRequest
    ): Response<AnimeResponse>
}

data class QueryRequest(
    val query: String,
    val variables: Map<String, Any>? = null
)

object ApiQueries {
    const val TOP_ANIME_QUERY = """
        query (\${page: Int = 1, \${perPage: Int = 20}) {
            Page(page: \$page, perPage: \$perPage) {
                pageInfo {
                    total
                    currentPage
                    lastPage
                    hasNextPage
                    perPage
                }
                media(type: ANIME, sort: POPULARITY_DESC) {
                    id
                    title {
                        romaji
                        english
                        native
                    }
                    coverImage {
                        large
                        medium
                        small
                    }
                    format
                    episodes
                    status
                    seasonYear
                    genres
                    averageScore
                    popularity
                    synopsis
                }
            }
        }
    """
    
    const val SEARCH_ANIME_QUERY = """
        query (\${search: String, \${page: Int = 1, \${perPage: Int = 20}) {
            Page(page: \$page, perPage: \$perPage) {
                pageInfo {
                    total
                    currentPage
                    lastPage
                    hasNextPage
                    perPage
                }
                media(type: ANIME, search: \$search) {
                    id
                    title {
                        romaji
                        english
                        native
                    }
                    coverImage {
                        large
                        medium
                        small
                    }
                    format
                    episodes
                    status
                    seasonYear
                    genres
                    averageScore
                    popularity
                    synopsis
                }
            }
        }
    """
    
    const val ANIME_DETAILS_QUERY = """
        query (\${id: Int) {
            Media(id: \$id, type: ANIME) {
                id
                title {
                    romaji
                    english
                    native
                }
                coverImage {
                    large
                    medium
                    small
                }
                format
                episodes
                status
                seasonYear
                genres
                averageScore
                popularity
                synopsis
                characters {
                    nodes {
                        id
                        name {
                            full
                            native
                        }
                        image {
                            large
                            medium
                        }
                    }
                }
            }
        }
    """
}
