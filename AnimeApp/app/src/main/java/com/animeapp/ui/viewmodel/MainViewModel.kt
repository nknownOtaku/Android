package com.animeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animeapp.data.api.AnimeApiService
import com.animeapp.data.model.Anime
import com.animeapp.data.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UiState(
    val animeList: List<Anime> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedAnime: Anime? = null
)

class MainViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    private val repository = AnimeRepository(AnimeApiService())
    
    init {
        loadTopAnime()
    }
    
    fun loadTopAnime() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val response = repository.getTopAnime()
            
            if (response.isSuccessful) {
                _uiState.value = _uiState.value.copy(
                    animeList = response.body() ?: emptyList(),
                    isLoading = false
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load anime. Please try again."
                )
            }
        }
    }
    
    fun searchAnime(query: String) {
        if (query.isBlank()) {
            loadTopAnime()
            return
        }
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val response = repository.searchAnime(query)
            
            if (response.isSuccessful) {
                _uiState.value = _uiState.value.copy(
                    animeList = response.body() ?: emptyList(),
                    isLoading = false
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "No results found"
                )
            }
        }
    }
    
    fun selectAnime(animeId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val response = repository.getAnimeDetails(animeId)
            
            if (response.isSuccessful) {
                _uiState.value = _uiState.value.copy(
                    selectedAnime = response.body(),
                    isLoading = false
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load anime details"
                )
            }
        }
    }
    
    fun clearSelectedAnime() {
        _uiState.value = _uiState.value.copy(selectedAnime = null)
    }
}
