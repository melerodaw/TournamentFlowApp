package navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ui.GameListScreen
import ui.HomeScreen
import ui.TournamentDetailScreen
import ui.TournamentListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToGames = { navController.navigate("games") },
                onNavigateToTournaments = { navController.navigate("tournaments") }
            )
        }

        composable("games") {
            GameListScreen(onBack = { navController.popBackStack() })
        }

        composable("tournaments") {
            TournamentListScreen(
                onBack = { navController.popBackStack() },
                onTournamentClick = { id -> navController.navigate("tournament/$id") }
            )
        }

        composable("tournament/{id}") { backStackEntry ->
            TournamentDetailScreen(
                tournamentId = backStackEntry.arguments?.getString("id")?.toInt() ?: 0,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
