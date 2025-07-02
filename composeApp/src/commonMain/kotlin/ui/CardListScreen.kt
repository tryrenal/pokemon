package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.Card
import viewmodel.CardViewModel
import viewmodel.CardUiState

@Composable
fun CardListScreen(
    viewModel: CardViewModel,
    onCardClick: (Card) -> Unit,
    onFavClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            Column {
                FloatingActionButton(onClick = onFavClick, modifier = Modifier.padding(bottom = 8.dp)) {
                    Text("Fav")
                }
                FloatingActionButton(onClick = onFilterClick) {
                    Text("Filter")
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            TextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.fetchCards(it)
                },
                placeholder = { Text("Search Pokémon") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            val favoriteSet = viewModel.favoriteSet.collectAsState().value
            when (val state = viewModel.state.collectAsState().value) {
                is CardUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is CardUiState.Success -> {
                    val cards = state.cards
                    if (cards.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No Pokémon found!", style = MaterialTheme.typography.titleLarge)
                        }
                    } else {
                        LazyColumn {
                            items(cards) { card ->
                                CardItem(
                                    card = card,
                                    isFavorite = favoriteSet.contains(card.id),
                                    onClick = { onCardClick(card) },
                                    onFavoriteClick = { viewModel.toggleFavorite(card.id) }
                                )
                            }
                        }
                    }
                }

                is CardUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Error: ${state.message}", color = Color.Red)
                    }
                }
            }
        }
    }
}

@Composable
fun CardItem(
    card: Card,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .shadow(4.dp, shape = RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = card.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Supertype: ${card.supertype}", style = MaterialTheme.typography.bodyMedium)
            }
            Text(
                text = if (isFavorite) "⭐" else "☆",
                modifier = Modifier
                    .clickable { onFavoriteClick() }
                    .padding(start = 8.dp)
            )
        }
    }
}