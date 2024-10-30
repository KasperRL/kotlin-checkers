package org.example.piece

import org.example.enums.Color
import org.example.util.Position

/**
 * Represents a piece on the board
 * @property color Color of the piece
 * @property position Position (x, y) of the piece
 * @property isKing True if the piece is a king
 * @property isCaptured True if the piece has been captured
 * @constructor
 */
data class Piece(
    val color: Color,
    var position: Position,
    val isKing: Boolean = false,
    var isCaptured: Boolean = false
)