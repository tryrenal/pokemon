package com.redveloper.pokemon

import androidx.navigation.compose.*
import repository.CardRepositoryImpl
import androidx.compose.material3.*
import androidx.compose.runtime.*
import network.HttpClientFactory
import viewmodel.CardViewModel
import usecase.GetCardsUseCase
import network.CardService
import ui.CardDetailScreen
import ui.CardListScreen
import ui.FavoriteScreen
import ui.FilterScreen
import model.Card

@Composable
actual fun App() {
    val client = HttpClientFactory().create()
    val service = CardService(client)
    val repository = CardRepositoryImpl(service)
    val useCase = GetCardsUseCase(repository)
    val viewModel = remember { CardViewModel(useCase) }

    val navController = rememberNavController()
    var selectedCard by remember { mutableStateOf<Card?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchCards()
    }

    MaterialTheme {
        NavHost(navController = navController, startDestination = "list") {
            composable("list") {
                CardListScreen(
                    viewModel = viewModel,
                    onCardClick = { card ->
                        selectedCard = card
                        navController.navigate("detail")
                    },
                    onFavClick = {
                        navController.navigate("favorite")
                    },
                    onFilterClick = {
                        navController.navigate("filter")
                    }
                )
            }

            composable("detail") {
                selectedCard?.let { CardDetailScreen(it) }
            }

            composable("favorite") {
                FavoriteScreen(viewModel) { card ->
                    selectedCard = card
                    navController.navigate("detail")
                }
            }

            composable("filter") {
                FilterScreen(viewModel) {
                    navController.popBackStack()
                    viewModel.fetchCards()
                }
            }
        }
    }
}