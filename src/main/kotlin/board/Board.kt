package org.example.board

import org.example.enums.Color
import org.example.piece.Piece
import org.example.util.Position

/**
 * Board class represents a checkers board with 8x8 squares
 * Squares are represented by a list of Square objects
 * Pieces are placed on the board by placing them on the squares
 * @see Square
 */
class Board {

    private val squares = createSquares()
    private var pieces = createInitialPieces()

    /**
     * Draw a board with 8x8 squares and pieces
     * Black squares are represented by [ ], white squares by [#]
     * Pieces are represented by their color's first letter
     * @return void
     */
    fun draw() {
        pieces.forEach { placePiece(it) }

        for (y in 0..8) {
            // print row numbers to the left
            if (y == 0) {
                print("  ")
            } else {
                print("$y ")
            }

            for (x in 1..8) {
                // print column numbers on the first row (at the top)
                if (y == 0) {
                    print(" $x ")
                    continue
                } else {
                    val square = getSquare(Position(x, y))
                    // Draw piece if the square is occupied, otherwise draw empty square
                    if (square?.isOccupied() == true) {
                        print("[${square.piece?.color?.name?.first()}]")
                    } else if (square?.color == Color.BLACK) {
                        print("[ ]")
                    } else {
                        print("[#]")
                    }
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

    /**
     * Create initial pieces for both sides
     * Black pieces on rows 1-3, white pieces on rows 6-8
     * @return List<Piece>
     */
    private fun createInitialPieces(): MutableList<Piece> {
        val pieces = mutableListOf<Piece>()

        // place black pieces on rows 1-3
        for (y in 1..3) {
            for (x in 1..8 step 2) {
                // add 1 to x if y is even to match the board's alternating pattern
                val position = Position(if (y % 2 == 0) x + 1 else x, y)
                pieces.add(Piece(Color.BLACK, position))
            }
        }

        // place white pieces on rows 6-8
        for (y in 6..8) {
            for (x in 1..8 step 2) {
                // add 1 to x if y is even to match the board's alternating pattern
                val position = Position(if (y % 2 == 0) x + 1 else x, y)
                pieces.add(Piece(Color.WHITE, position))
            }
        }

        return pieces
    }

    /**
     * Get a square by its position
     * @param position Position of the square
     * @return Square?
     */
    fun getSquare(position: Position): Square? {
        return squares.find { it.position == position }
    }

    /**
     * Get a square by the piece on it
     * @param piece Piece to find the square for
     * @return Square?
     */
    fun getSquare(piece: Piece): Square? {
        return squares.find { it.piece == piece }
    }

    /**
     * Get a piece by its position
     * @param position Position of the piece
     * @return Piece?
     */
    fun getPiece(position: Position): Piece? {
        return getSquare(position)?.piece
    }

    /**
     * Get all pieces of a specific color
     * @param color Color of the pieces
     * @return List<Piece>
     */
    fun getPieces(color: Color): List<Piece> {
        return pieces.filter { it.color == color }
    }

    /**
     * Place a piece on the board
     * @param piece Piece to be placed
     * @return void
     */
    fun placePiece(piece: Piece) {
        val square = getSquare(piece.position)
        square?.piece = piece
    }

    /**
     * Move a piece from one square to another
     * @param piece Piece to move
     * @param square Destination square
     * @return void
     */
    fun movePiece(piece: Piece, square: Square) {
        val currentSquare = getSquare(piece)
        currentSquare?.piece = null
        square.piece = piece
        piece.position = square.position
    }

    /**
     * Remove a piece from the board
     * @param piece Piece
     * @return void
     */
    fun removePiece(piece: Piece) {
        val square = getSquare(piece)
        square?.piece = null
        pieces.remove(piece)
    }

}
