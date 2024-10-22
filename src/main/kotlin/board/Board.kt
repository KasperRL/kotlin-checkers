package org.example.board

import org.example.enums.Color
import org.example.piece.Piece
import org.example.util.Position

class Board {
    private val squares = createSquares()

    /**
     * Place a piece on the board
     * @param piece Piece
     * @return void
     */
    fun placePiece(piece: Piece) {
        val square = squares.find { it.position == piece.position }
        square?.piece = piece
    }

    /**
     * Draw a board with 8x8 squares and pieces
     * Black squares are represented by [ ], white squares by [#]
     * Pieces are represented by their color's first letter
     * @return void
     */
    fun draw() {
        for (y in 1..8) {
            for (x in 1..8) {
                // Find the square with the coordinates
                val square = squares.find { it.position.x == x && it.position.y == y }
                // Draw piece if the square is occupied, otherwise draw empty square
                if (square?.isOccupied() == true) {
                    print("[${square.piece?.color?.name?.first()}]")
                } else if (square?.color == Color.BLACK) {
                    print("[ ]")
                } else {
                    print("[#]")
                }
            }
            println()
        }
    }

    /**
     * Create 64 squares with their respective colors and coordinates
     * @return List<Square>
     */
    private fun createSquares(): List<Square> {
        val squares = mutableListOf<Square>()
        for (y in 1..8) {
            for (x in 1..8) {
                // If sum of x and y is even, the square is black, otherwise it is white
                val color = if ((x + y) % 2 == 0) Color.BLACK else Color.WHITE
                val position = Position(x, y)
                squares.add(Square(position, color))
            }
        }

        return squares
    }

}