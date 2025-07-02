package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import viewmodel.CardViewModel

@Composable
fun FilterScreen(
    viewModel: CardViewModel,
    onApply: () -> Unit
) {
    var selectedType by remember { mutableStateOf<String?>(null) }
    var selectedHp by remember { mutableStateOf<String?>(null) }
    var selectedSort by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
        .padding(top = 32.dp)) {

        Text(text = "Filter Options", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Type Filter")
        Row {
            listOf("Fire", "Water", "Grass", "Electric", "Psychic").forEach { type ->
                FilterChip(type, selectedType == type) {
                    selectedType = if (selectedType == type) null else type
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("HP Filter")
        Row {
            listOf("50", "100", "150").forEach { hp ->
                FilterChip(hp, selectedHp == hp) {
                    selectedHp = if (selectedHp == hp) null else hp
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Sort By")
        Row {
            listOf("Name", "HP").forEach { sort ->
                FilterChip(sort, selectedSort == sort) {
                    selectedSort = if (selectedSort == sort) null.toString() else sort
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            viewModel.setTypeFilter(selectedType)
            viewModel.setHpFilter(selectedHp)
            viewModel.setSortOption(selectedSort)
            onApply()
        }) {
            Text("Apply Filters")
        }
    }
}

@Composable
fun FilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = if (isSelected) Color(0xFF7E57C2) else Color.Transparent,
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}