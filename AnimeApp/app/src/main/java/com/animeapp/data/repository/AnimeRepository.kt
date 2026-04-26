package com.animeapp.data.repository

import com.animeapp.data.api.AnimeApiService
import com.animeapp.data.api.ApiQueries
import com.animeapp.data.api.QueryRequest
import com.animeapp.data.model.Anime
import retrofit2.Response

class AnimeRepository(
    private val apiService: AnimeApiService
) {
    
    suspend fun getTopAnime(page: Int = 1, perPage: Int = 20): Response<List<Anime>> {
        val variables = mapOf(
            "page" to page,
            "perPage" to perPage
        )
        val query = QueryRequest(ApiQueries.TOP_ANIME_QUERY, variables)
        
        return try {
            val response = apiService.getTopAnime(query)
            if (response.isSuccessful) {
                val animeList = response.body()?.data?.page?.media ?: emptyList()
                Response.success(animeList)
            } else {
                Response.error(response.code(), response.errorBody())
            }
        } catch (e: Exception) {
            Response.error(500, null)
        }
    }
    
    suspend fun searchAnime(searchQuery: String, page: Int = 1, perPage: Int = 20): Response<List<Anime>> {
        val variables = mapOf(
            "search" to searchQuery,
            "page" to page,
            "perPage" to perPage
        )
        val query = QueryRequest(ApiQueries.SEARCH_ANIME_QUERY, variables)
        
        return try {
            val response = apiService.searchAnime(query)
            if (response.isSuccessful) {
                val animeList = response.body()?.data?.page?.media ?: emptyList()
                Response.success(animeList)
            } else {
                Response.error(response.code(), response.errorBody())
            }
        } catch (e: Exception) {
            Response.error(500, null)
        }
    }
    
    suspend fun getAnimeDetails(animeId: Int): Response<Anime?> {
        val variables = mapOf("id" to animeId)
        val query = QueryRequest(ApiQueries.ANIME_DETAILS_QUERY, variables)
        
        return try {
            val response = apiService.getAnimeDetails(query)
            if (response.isSuccessful) {
                val anime = response.body()?.data?.page?.media?.firstOrNull()
                Response.success(anime)
            } else {
                Response.error(response.code(), response.errorBody())
            }
        } catch (e: Exception) {
            Response.error(500, null)
        }
    }
}
