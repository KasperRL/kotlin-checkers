package org.example.player

import org.example.enums.Color

/**
 * Player class represents a player in the game
 * @property name player's name
 * @property color player's color
 */
data class Player(
    val name: String,
    val color: Color
)