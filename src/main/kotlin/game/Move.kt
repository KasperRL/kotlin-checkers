package org.example.game

import org.example.piece.Piece
import org.example.util.Position

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
        return capturedPiece != null
    }
}