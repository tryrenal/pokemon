package network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import model.CardResponse

class CardService(
    private val client: HttpClient
) {
    suspend fun getCards(query: String? = null): CardResponse {
        val baseUrl = "https://api.pokemontcg.io/v2/cards"
        val response: HttpResponse = client.get(baseUrl) {
            if (!query.isNullOrEmpty()) {
                url { parameters.append("q", query)}
            }
        }
        return response.body()
    }
}