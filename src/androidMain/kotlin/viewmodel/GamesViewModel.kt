package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.ApiService
import api.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GamesViewModel : ViewModel() {
    private val apiService = ApiService()

    val games = MutableStateFlow<List<Game>>(emptyList())
    val isLoading = MutableStateFlow(false)
    val error = MutableStateFlow<String?>(null)

    init {
        loadGames()
    }

    fun loadGames() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null
            try {
                val gamesList = apiService.getGames()
                games.value = gamesList
            } catch (e: Exception) {
                error.value = e.message ?: "Error loading games"
                games.value = emptyList()
            } finally {
                isLoading.value = false
            }
        }
    }
}
