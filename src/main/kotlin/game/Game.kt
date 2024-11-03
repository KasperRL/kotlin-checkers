package org.example.game

import org.example.board.Board
import org.example.board.Square
import org.example.enums.Color
import org.example.piece.Piece
import org.example.player.Player
import org.example.util.Position

/**
 * Game class represents a checkers game
 * It creates a board and manages the overall game state
 * @see Board
 * @see Piece
 */
class Game {

    val players = createPlayers()
    val board = Board()
    var currentPlayerIndex = players.indexOf(players.first { it.color == Color.WHITE })

    /**
     * Start the game
     * @return void
     */
    fun start() {
        board.draw()

        while (!isGameOver()) {
            playTurn()
        }

        switchPlayer()
        val winner = players[currentPlayerIndex]
        println("${winner.name} wins!")
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

            // the first player gets to choose their color
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
        return board.getPieces(Color.BLACK).isEmpty() || board.getPieces(Color.WHITE).isEmpty()
    }

    /**
     * Play a turn for the current player
     * @return void
     */
    private fun playTurn() {
        val currentPlayer = players[currentPlayerIndex]
        println("${currentPlayer.name}'s turn (${currentPlayer.color})")
        val validator = Validator(board)

        val piece = selectPiece() ?: return
        if (!validator.pieceBelongsToPlayer(piece, currentPlayer)) {
            println("That piece doesn't belong to you")
            return
        }

        val square = selectSquare() ?: return
        val move = Move(piece, square.position)

        // if any pieces can capture, the player must capture
        val availableCaptures = board.getPieces(currentPlayer.color).flatMap { availableCaptures(it) }
        if (availableCaptures.isNotEmpty() && !move.isCapture()) {
            println("You must capture a piece. Available jump moves: $availableCaptures")
            return
        }

        if (move.isCapture()) {
            move.capturedPiece = getCapturedPiece(piece, square.position, currentPlayer.color)
            if (!validator.isValidCapture(move)) {
                println("The piece can't move to that square")
                return
            }
        } else if (!validator.isValidMove(move)) {
            println("The piece can't move to that square")
            return
        }

        move.capturedPiece?.let { board.removePiece(it) }
        board.movePiece(piece, square)
        board.draw()
        switchPlayer()
    }

    /**
     * Get the captured piece
     * The piece is retrieved by getting the piece diagonal to the players piece
     * @param piece Piece
     * @param destination Position
     * @param playerColor Color
     * @return Piece?
     */
    private fun getCapturedPiece(piece: Piece, destination: Position, playerColor: Color): Piece? {
        val captureX = (piece.position.x + destination.x) / 2 // get the captured piece's x
        val captureY = if (playerColor == Color.BLACK) {
            piece.position.y + 1 // capture piece is below the player's piece
        } else {
            piece.position.y - 1 // capture piece is above the player's piece
        }

        return board.getPiece(Position(captureX, captureY))
    }

    /**
     * Get available captures for a piece.
     * A piece can capture another piece if it's diagonal to it and the square after is empty.
     * @param piece Piece
     * @return List<Move>
     */
    private fun availableCaptures(piece: Piece): List<Move> {
        val availableCaptures = mutableListOf<Move>()
        val direction = if (piece.color == Color.BLACK) 1 else -1 // black pieces move down, white pieces move up

        // get the pieces on the diagonal squares if any
        val diagonalPieces = listOf(
            board.getPiece(Position(piece.position.x + 1, piece.position.y + direction)),
            board.getPiece(Position(piece.position.x - 1, piece.position.y + direction))
        )

        diagonalPieces.forEach {
            if (it != null && it.color != piece.color) {
                // get the square after the piece (destination square)
                val destX = piece.position.x + 2 * (it.position.x - piece.position.x)
                val destY = piece.position.y + 2 * (it.position.y - piece.position.y)
                val destSquare = board.getSquare(Position(destX, destY))

                if (destSquare != null && !destSquare.isOccupied()) {
                    availableCaptures.add(Move(piece, destSquare.position, it))
                }
            }
        }

        return availableCaptures
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
            val piece = board.getPiece(Position(x, y))

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
