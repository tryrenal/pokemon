package model

import kotlinx.serialization.Serializable

@Serializable
data class CardResponse(
    val data: List<Card>
)

@Serializable
data class Card(
    val id: String,
    val name: String,
    val supertype: String,
    val subtypes: List<String>? = null,
    val level: String? = null,
    val hp: String? = null,
    val types: List<String>? = null,
    val evolvesFrom: String? = null,
    val abilities: List<Ability>? = null,
    val attacks: List<Attack>? = null,
    val weaknesses: List<Weakness>? = null,
    val images: CardImages? = null
)

@Serializable
data class Ability(
    val name: String,
    val text: String,
    val type: String
)

@Serializable
data class Attack(
    val name: String,
    val cost: List<String>,
    val convertedEnergyCost: Int,
    val damage: String,
    val text: String?
)

@Serializable
data class Weakness(
    val type: String,
    val value: String
)

@Serializable
data class CardImages(
    val small: String,
    val large: String
)