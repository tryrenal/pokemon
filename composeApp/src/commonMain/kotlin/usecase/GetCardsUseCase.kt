package usecase

import model.Card
import repository.CardRepository

class GetCardsUseCase(private val repository: CardRepository) {
    suspend operator fun invoke(query: String? = null): List<Card> {
        return repository.getCards(query)
    }
}