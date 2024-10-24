package org.example.game

import org.example.board.Board
import org.example.board.Square
import org.example.enums.Color
import org.example.piece.Piece
import org.example.player.Player
import org.example.util.Position

/**
 * Game class represents a checkers game
 * It creates a board and places initial pieces on it
 * @see Board
 * @see Piece
 */
class Game {

    val players = createPlayers()

    /**
     * Start the game
     * @return void
     */
    fun start() {
        val board = Board()
        val pieces = createInitialPieces()

        pieces.forEach { board.placePiece(it) }
        board.draw()
    }

    /**
     * Create initial pieces for both sides
     * Black pieces on rows 1-3, white pieces on rows 6-8
     * @return List<Piece>
     */
    private fun createInitialPieces(): List<Piece> {
        val pieces = mutableListOf<Piece>()

        // Place black pieces on rows 1-3
        for (y in 1..3) {
            for (x in 1..8 step 2) {
                // add 1 to x if y is even to match the board's alternating pattern
                val position = Position(if (y % 2 == 0) x + 1 else x, y)
                pieces.add(Piece(Color.BLACK, position))
            }
        }

        // Place white pieces on rows 6-8
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
     * Interactively create the players
     * @return List<Player>
     */
    fun createPlayers(): List<Player> {
        val players = mutableListOf<Player>()
        for (i in 1..2) {
            print("Player $i, enter your name: ")
            val name = readln()

            // just the first player gets to choose a color
            val color: Color
            if (i == 1) {
                print("$name, enter your color (black/white): ")
                color = when (readln().lowercase()) {
                    "black" -> Color.BLACK
                    "white" -> Color.WHITE
                    else -> Color.WHITE // default to white if invalid input
                }
            } else {
                // player 2 gets the opposite color of player 1
                color = when (players[0].color) {
                    Color.BLACK -> Color.WHITE
                    Color.WHITE -> Color.BLACK
                }
            }

            players.add(Player(name, color))
        }

        return players
    }

}