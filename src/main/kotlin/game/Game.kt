package org.example.game

import org.example.board.Board
import org.example.board.Square
import org.example.enums.Color

class Game {

    /**
     * Start the game
     * @return void
     */
    fun start() {
        val board = Board(createSquares())
        board.draw()
    }
    
    /**
     * Create 64 squares with their respective colors
     * @return List<Square>
     */
    private fun createSquares(): List<Square> {
        val squares = mutableListOf<Square>()
        for (x in 1..8) {
            for (y in 1..8) {
                // If sum of x and y is even, the square is black, otherwise it is white
                val color = if ((x + y) % 2 == 0) Color.BLACK else Color.WHITE
                squares.add(Square(x, y, color))
            }
        }
        return squares
    }

}