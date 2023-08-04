package com.example.numberscomposition.domain.models

import java.io.Serializable

data class GameSettings(
    val maxSumValue: Int,
    val minCountAnswersForWinner: Int,
    val minPercentAnswersForWinner: Int,
    val timeGameInSeconds: Int
): Serializable