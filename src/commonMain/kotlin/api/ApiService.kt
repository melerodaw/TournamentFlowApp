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
        println("Llamando a $BASE_URL/api/juegos")
        return try {
            val result: List<Game> = client.get("$BASE_URL/api/juegos").body()
            println("Respuesta: $result")
            result
        } catch (e: Exception) {
            println("Error llamando a $BASE_URL/api/juegos: ${e.message}")
            throw e
        }
    }

    suspend fun getTournaments(): List<Tournament> {
        println("Llamando a $BASE_URL/api/torneos")
        return try {
            val result: List<Tournament> = client.get("$BASE_URL/api/torneos").body()
            println("Respuesta: $result")
            result
        } catch (e: Exception) {
            println("Error llamando a $BASE_URL/api/torneos: ${e.message}")
            throw e
        }
    }

    suspend fun getTournamentDetail(id: Int): TournamentDetail? {
        println("Llamando a $BASE_URL/api/torneos/$id")
        return try {
            val result: TournamentDetail = client.get("$BASE_URL/api/torneos/$id").body()
            println("Respuesta: $result")
            result
        } catch (e: Exception) {
            println("Error llamando a $BASE_URL/api/torneos/$id: ${e.message}")
            throw e
        }
    }
}
