package api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

const val BASE_URL = "http://10.0.2.2:8000"

@Serializable
data class Game(
    val id: Int,
    val name: String,
    val imagePath: String
)

@Serializable
data class GameSummary(
    val id: Int,
    val name: String,
    val imagePath: String
)

@Serializable
data class UserSummary(
    val id: Int,
    val username: String
)

@Serializable
data class Tournament(
    val id: Int,
    val name: String,
    val status: String,
    val format: String,
    val maxParticipants: Int,
    val participantsCount: Int,
    val startAt: String,
    val game: GameSummary? = null,
    val organizer: UserSummary,
    val champion: UserSummary? = null
)

@Serializable
data class TournamentDetail(
    val id: Int,
    val name: String,
    val status: String,
    val format: String,
    val maxParticipants: Int,
    val participantsCount: Int,
    val startAt: String,
    val game: GameSummary? = null,
    val organizer: UserSummary,
    val champion: UserSummary? = null,
    val participants: List<UserSummary> = emptyList()
)

class ApiService {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getGames(): List<Game> {
        return try {
            client.get("$BASE_URL/games").body()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getTournaments(): List<Tournament> {
        return try {
            client.get("$BASE_URL/tournaments").body()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getTournamentDetail(id: Int): TournamentDetail? {
        return try {
            client.get("$BASE_URL/tournaments/$id").body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
