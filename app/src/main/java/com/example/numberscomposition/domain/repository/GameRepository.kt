package com.example.numberscomposition.domain.repository

import com.example.numberscomposition.domain.models.GameSettings
import com.example.numberscomposition.domain.models.Level
import com.example.numberscomposition.domain.models.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countAllAnswers: Int): Question

    fun getGameSettings(level: Level): GameSettings

}