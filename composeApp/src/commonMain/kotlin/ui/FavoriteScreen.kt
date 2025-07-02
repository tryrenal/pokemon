package ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewmodel.CardViewModel
import model.Card

@Composable
fun FavoriteScreen(
    viewModel: CardViewModel,
    onCardClick: (Card) -> Unit
) {
    val favCards = viewModel.getFavoriteCards()

    if (favCards.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("⭐", style = MaterialTheme.typography.displayMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text("No favorites yet!", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Add Pokémon to your favorites to see them here.", style = MaterialTheme.typography.bodyMedium)
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp)
        ) {
            items(favCards) { card ->
                CardItem(
                    card = card,
                    isFavorite = true,
                    onClick = { onCardClick(card) },
                    onFavoriteClick = { viewModel.toggleFavorite(card.name) }
                )
            }
        }
    }
}