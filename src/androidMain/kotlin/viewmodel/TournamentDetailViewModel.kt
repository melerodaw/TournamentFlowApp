package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.ApiService
import api.TournamentDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TournamentDetailViewModel(
    private val tournamentId: Int
) : ViewModel() {
    private val apiService = ApiService()

    val tournament = MutableStateFlow<TournamentDetail?>(null)
    val isLoading = MutableStateFlow(false)
    val error = MutableStateFlow<String?>(null)

    init {
        loadDetail()
    }

    fun loadDetail() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null
            try {
                val detail = apiService.getTournamentDetail(tournamentId)
                tournament.value = detail
            } catch (e: Exception) {
                error.value = e.message ?: "Error loading tournament details"
                tournament.value = null
            } finally {
                isLoading.value = false
            }
        }
    }
}
