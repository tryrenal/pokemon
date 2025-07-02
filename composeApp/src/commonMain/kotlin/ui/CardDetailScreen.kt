package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Card

@Composable
fun CardDetailScreen(card: Card) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        card.images?.let {
            KMPImage(
                url = it.small,
                contentDescription = card.name
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = card.name, style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Supertype: ${card.supertype}", style = MaterialTheme.typography.bodyMedium)

        card.subtypes?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Subtypes: ${it.joinToString()}", style = MaterialTheme.typography.bodyMedium)
        }

        card.hp?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "HP: $it", style = MaterialTheme.typography.bodyMedium)
        }

        card.abilities?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Abilities: ${it.joinToString { ab -> ab.name ?: "" }}", style = MaterialTheme.typography.bodyMedium)
        }

        card.attacks?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Attacks: ${it.joinToString { atk -> atk.name ?: "" }}", style = MaterialTheme.typography.bodyMedium)
        }

        card.weaknesses?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Weaknesses: ${it.joinToString { wk -> wk.type ?: "" }}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}