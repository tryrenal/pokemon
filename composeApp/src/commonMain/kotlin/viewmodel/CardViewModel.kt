package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import usecase.GetCardsUseCase
import model.Card

class CardViewModel(private val getCardsUseCase: GetCardsUseCase) {
    private val _state = MutableStateFlow<CardUiState>(CardUiState.Loading)
    private val viewModelScope: CoroutineScope = MainScope()
    val state: StateFlow<CardUiState> = _state

    var favoriteSet = MutableStateFlow(setOf<String>())
        private set

    var currentSort = MutableStateFlow("")
        private set

    var currentTypeFilter = MutableStateFlow<String?>(null)
        private set

    var currentHpFilter = MutableStateFlow<String?>(null)
        private set

    private var _allCards = listOf<Card>()

    fun setSortOption(option: String) {
        currentSort.value = option
    }

    fun setTypeFilter(type: String?) {
        currentTypeFilter.value = type
    }

    fun setHpFilter(hp: String?) {
        currentHpFilter.value = hp
    }

    fun fetchCards(query: String? = null) {
        viewModelScope.launch {
            _state.value = CardUiState.Loading
            try {
                var cards = getCardsUseCase(query)

                _allCards = cards

                currentTypeFilter.value?.let { type ->
                    cards = cards.filter { it.types?.contains(type) == true }
                }

                currentHpFilter.value?.let { hp ->
                    cards = cards.filter { it.hp == hp }
                }

                when (currentSort.value) {
                    "Name" -> cards = cards.sortedBy { it.name }
                    "HP" -> cards = cards.sortedBy { it.hp }
                }

                _state.value = CardUiState.Success(cards)
            } catch (e: Exception) {
                _state.value = CardUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun toggleFavorite(cardId: String) {
        favoriteSet.value = if (favoriteSet.value.contains(cardId)) {
            favoriteSet.value - cardId
        } else {
            favoriteSet.value + cardId
        }
    }

    fun getFavoriteCards(): List<Card> {
        return _allCards.filter { favoriteSet.value.contains(it.id) }
    }
}

sealed class CardUiState {
    data object Loading : CardUiState()
    data class Success(val cards: List<Card>) : CardUiState()
    data class Error(val message: String) : CardUiState()
}