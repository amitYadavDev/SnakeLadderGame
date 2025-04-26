package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.GameState
import com.example.myapplication.model.Ladder
import com.example.myapplication.model.Player
import com.example.myapplication.model.Snake
import kotlinx.coroutines.flow.MutableStateFlow

class SnakeLadderViewModel : ViewModel() {

    private val _gameState = MutableStateFlow(GameState())
    val gameState get() = _gameState

    init {
        _gameState.value = GameState(
            players = listOf(Player(1, 1), Player(2, 1)),
            snakes = listOf(Snake(99, 21), Snake(72, 34)),
            ladders = listOf(Ladder(3, 22), Ladder(15, 44))
        )
    }


    fun rollDice() {
        val state = _gameState.value
        var currPos = state.currPosition

        var playerPos = state.players[currPos].pos
        val diceRoll = (1..6).random()

        var newPos = playerPos + diceRoll

        state.snakes.find { it.head == newPos }?.let { newPos = it.tail }
        state.ladders.find { it.start == newPos }?.let { newPos = it.end }


        if (newPos <= 100) playerPos = newPos

        if (newPos == 100) {
            //win
            return
        } else {
            currPos = (currPos + 1) % state.players.size
        }
        val updatedPlayers = state.players.map {
            if(it.id == state.players[currPos].id) it.copy(pos = playerPos)
            else it
        }

        val newS =
            GameState(snakes = state.snakes, players = updatedPlayers, ladders = state.ladders).copy(
                currPosition = currPos,
                diceNumber = diceRoll
            )

        _gameState.value = newS
        Log.i("rollDice", "${_gameState.value}")
    }
}