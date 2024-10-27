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
    val board = Board()
    val pieces = createInitialPieces()
    var currentPlayerIndex = 0

    /**
     * Start the game
     * Place initial pieces on the board and start the main game loop
     * @return void
     */
    fun start() {
        pieces.forEach { board.placePiece(it) }
        board.draw()

        while (!isGameOver()) {
            playTurn()
        }
    }

    /**
     * Create initial pieces for both sides
     * Black pieces on rows 1-3, white pieces on rows 6-8
     * @return List<Piece>
     */
    private fun createInitialPieces(): List<Piece> {
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
     * Interactively create the players
     * @return List<Player>
     */
    private fun createPlayers(): List<Player> {
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

    /**
     * Check if the game is over
     * @return Boolean
     */
    fun isGameOver(): Boolean {
        return false
    }

    /**
     * Play a turn for the current player
     * @return void
     */
    private fun playTurn() {
        val currentPlayer = players[currentPlayerIndex]
        println("${currentPlayer.name}'s turn (${currentPlayer.color})")
        val validator = Validator()

        val piece = selectPiece() ?: return
        if (!validator.pieceBelongsToPlayer(piece, currentPlayer)) {
            println("That piece doesn't belong to you")
            return
        }

        val square = selectSquare() ?: return
        if (!validator.isValidMove(piece, square)) {
            println("The piece can't move to that square")
            return
        }

        board.movePiece(piece, square)
        board.draw()
        switchPlayer()
    }

    /**
     * Prompt the user to select a piece to move
     * @return Piece?
     */
    private fun selectPiece(): Piece? {
        print("Enter the coordinates of the piece to move (x,y): ")

        return try {
            val coordinates = readln()
            val (x, y) = coordinates.split(",").map { it.trim().toInt() } // split input to x and y
            val piece = pieces.find { it.position == Position(x, y) }

            if (piece == null) {
                println("No piece found at $x, $y")
                null // return null to prompt the user to select a piece again
            } else {
                piece
            }
        } catch (_: Exception) {
            println("Invalid input. Please enter the coordinates in the format x,y")
            null
        }
    }

    /**
     * Prompt the user to select a square to move to
     * @return Square?
     */
    private fun selectSquare(): Square? {
        print("Enter the coordinates of the square to move to (x,y): ")

        return try {
            val destCoordinates = readln()
            val (destX, destY) = destCoordinates.split(",").map { it.trim().toInt() } // split input to x and y
            val square = board.getSquare(Position(destX, destY))

            if (square == null) {
                println("No square found at $destX, $destY")
                null // return null to prompt the user to select a square again
            } else {
                square
            }
        } catch (_: Exception) {
            println("Invalid input. Please enter the coordinates in the format x,y")
            null
        }
    }

    /**
     * Switch the current player
     * @return void
     */
    private fun switchPlayer() {
        currentPlayerIndex = if (currentPlayerIndex == 0) 1 else 0
    }

}