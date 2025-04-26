package com.example.myapplication.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.viewmodel.SnakeLadderViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = ViewModelProvider(this).get(SnakeLadderViewModel::class.java)
            SnakeLadder(viewModel)
        }
    }
}

@Composable
fun SnakeLadder(viewModel: SnakeLadderViewModel) {

    // Observe game state from ViewModel
    val gameState by viewModel.gameState.collectAsState()

    LaunchedEffect(gameState.currPosition) {
        Log.i("rollDice11", "${gameState}")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(10),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(100) { index ->
                val cellNumber = 100 - index
                val isOccupied = gameState.players.any { it.pos == cellNumber }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(1.dp, Color.Black)
                        .background(if (isOccupied) Color.Green else Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text("$cellNumber")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("dice: ${gameState.diceNumber}")
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { viewModel.rollDice() }) {
                Text("Roll Dice")
            }
            Text("Player ${gameState.players[gameState.currPosition].id}'s Turn")
        }
    }
}