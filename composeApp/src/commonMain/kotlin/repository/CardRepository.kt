package repository

import model.Card
import network.CardService

interface CardRepository {
    suspend fun getCards(query: String? = null): List<Card>
}

class CardRepositoryImpl(private val service: CardService): CardRepository {
    override suspend fun getCards(query: String?): List<Card> {
        return service.getCards(query).data
    }
}