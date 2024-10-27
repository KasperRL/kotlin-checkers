package org.example.game

import org.example.board.Square
import org.example.enums.Color
import org.example.piece.Piece
import org.example.player.Player

/**
 * Validator class is responsible for validating moves
 * @see Piece
 * @see Square
 */
class Validator {
    /**
     * Check if the move is valid
     * A move is valid if the destination is in front of the piece and diagonal to it
     * @param piece Piece
     * @param destination Square
     * @return Boolean
     */
    fun isValidMove(piece: Piece, destination: Square): Boolean {
        // depending on the color, the piece can either only move up or only move down
        return if (piece.color == Color.WHITE) {
            destination.position.y < piece.position.y // destination is in front of the piece
                    && (piece.position.x + 1 == destination.position.x || piece.position.x - 1 == destination.position.x) // destination is diagonal to the piece
        } else {
            destination.position.y > piece.position.y
                    && (piece.position.x + 1 == destination.position.x || piece.position.x - 1 == destination.position.x)
        }
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