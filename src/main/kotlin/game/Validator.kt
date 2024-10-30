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
        val offsetX = move.to.x - move.piece.position.x
        val offsetY = move.to.y - move.piece.position.y

        return when (move.piece.color) {
            Color.WHITE -> offsetY == -1 && (offsetX == 1 || offsetX == -1)
            Color.BLACK -> offsetY == 1 && (offsetX == 1 || offsetX == -1)
        }
    }

    /**
     * Check if the move is a valid capture move
     * A capture move is valid if the destination is two squares away from the piece and the square in between is occupied
     * @param move Move
     * @return Boolean
     */
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