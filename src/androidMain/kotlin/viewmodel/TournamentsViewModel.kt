package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.ApiService
import api.Tournament
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TournamentsViewModel : ViewModel() {
    private val apiService = ApiService()

    val tournaments = MutableStateFlow<List<Tournament>>(emptyList())
    val isLoading = MutableStateFlow(false)
    val error = MutableStateFlow<String?>(null)

    init {
        loadTournaments()
    }

    fun loadTournaments() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null
            try {
                val tournamentsList = apiService.getTournaments()
                tournaments.value = tournamentsList
            } catch (e: Exception) {
                error.value = e.message ?: "Error loading tournaments"
                tournaments.value = emptyList()
            } finally {
                isLoading.value = false
            }
        }
    }
}
