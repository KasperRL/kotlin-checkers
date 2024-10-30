package org.example.util

/**
 * Represents a position on the board
 * @property x column
 * @property y row
 * @constructor
 */
data class Position(
    val x: Int,
    val y: Int
) {
    override fun toString(): String {
        return "($x, $y)"
    }
}