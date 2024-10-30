package org.example.game

import org.example.piece.Piece
import org.example.util.Position
import kotlin.math.abs

data class Move(
    val piece: Piece,
    val to: Position,
    var capturedPiece: Piece? = null
) {
    /**
     * Check if the move is a capture move
     * @return Boolean
     */
    fun isCapture(): Boolean {
        return abs(to.x - piece.position.x) == 2 && abs(to.y - piece.position.y) == 2
    }

    override fun toString(): String {
        return "${piece.position} -> $to"
    }
}