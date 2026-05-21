package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TournamentDetailViewModelFactory(private val tournamentId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TournamentDetailViewModel(tournamentId) as T
    }
}
