package org.example.board

import org.example.enums.Color

data class Board(
    val squares: List<Square>
) {

    /**
     * Draw a 8x8 board
     * @return void
     */
    fun draw() {
        for (i in 1..8) {
            for (j in 1..8) {
                // Find the square with the coordinates, draw it with its corresponding color
                val square = squares.find { it.x == i && it.y == j }
                if (square?.color == Color.BLACK) {
                    print("[#]")
                } else {
                    print("[ ]")
                }
            }
            println()
        }
    }

}