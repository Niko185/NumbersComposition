package com.example.numberscomposition.domain.models

import java.io.Serializable

data class GameResult(
    val isWinner: Boolean,
    val countRightAnswers: Int,
    val countAllAnswers: Int,
    val gameSettings: GameSettings
): Serializable
