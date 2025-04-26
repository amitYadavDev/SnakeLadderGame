package com.example.myapplication.model

data class GameState(
    val players: List<Player> = listOf(),
    val snakes: List<Snake> = listOf(),
    val ladders: List<Ladder> = listOf(),
    var currPosition: Int = 0,
    var diceNumber: Int = 0
)
