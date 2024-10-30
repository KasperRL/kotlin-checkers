package org.example.game

import org.example.board.Board
import org.example.board.Square
import org.example.enums.Color
import org.example.piece.Piece
import org.example.player.Player

/**
 * Validator class is responsible for validating moves
 * @see Piece
 * @see Square
 */
class Validator(val board: Board) {

    /**
     * Check if the move is valid
     * A move is valid if the destination is in front of the piece and diagonal to it
     * @param move Move
     * @return Boolean
     */
    fun isValidMove(move: Move): Boolean {
        // depending on the color, the piece can either only move up or only move down
        return if (move.piece.color == Color.WHITE) {
            move.to.y == move.piece.position.y - 1 // destination is in front of the piece
                    && (move.piece.position.x + 1 == move.to.x || move.piece.position.x - 1 == move.to.x) // destination is diagonal to the piece
        } else {
            move.to.y == move.piece.position.y + 1
                    && (move.piece.position.x + 1 == move.to.x || move.piece.position.x - 1 == move.to.x)
        }
    }

    fun isValidCapture(move: Move): Boolean {
        val targetSquare = board.getSquare(move.to) ?: return false
        return !targetSquare.isOccupied() && move.capturedPiece != null
    }

    /**
     * Check if the piece belongs to the player
     * @param piece Piece
     * @param player Player
     * @return Boolean
     */
    fun pieceBelongsToPlayer(piece: Piece, player: Player): Boolean {
        return piece.color == player.color
    }

}