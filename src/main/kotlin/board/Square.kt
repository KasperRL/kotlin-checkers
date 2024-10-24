package org.example.board

import org.example.enums.Color
import org.example.piece.Piece
import org.example.util.Position

/**
 * Represents a square on the board
 * @property position Position (x, y)
 * @property color Color of the square
 * @property piece Piece on the square, null if empty
 * @constructor
 */
data class Square(
    val position: Position,
    val color: Color,
    var piece: Piece? = null
) {

    /**
     * Check if the square is occupied by a piece
     * @return Boolean
     */
    fun isOccupied(): Boolean {
        return piece != null
    }

}