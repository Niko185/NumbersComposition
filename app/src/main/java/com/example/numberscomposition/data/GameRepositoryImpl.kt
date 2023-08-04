package com.example.numberscomposition.data

import com.example.numberscomposition.domain.models.GameSettings
import com.example.numberscomposition.domain.models.Level
import com.example.numberscomposition.domain.models.Question
import com.example.numberscomposition.domain.repository.GameRepository
import java.lang.Integer.max
import java.lang.Math.min
import kotlin.random.Random

//object для того чтобы везде использовался один объект репозитория.
object GameRepositoryImpl: GameRepository {
    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countAllAnswers: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val answersVariant = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        answersVariant.add(rightAnswer)
        val from = max(rightAnswer - countAllAnswers, MIN_ANSWER_VALUE)
        val to = min(maxSumValue, rightAnswer + countAllAnswers)
        while(answersVariant.size < countAllAnswers) {
            answersVariant.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, answersVariant.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level) {
            Level.TEST -> GameSettings(10, 3, 50,8)
            Level.EASY -> GameSettings(20, 10, 70,60)
            Level.NORMAL -> GameSettings(30,20, 80, 50)
            Level.HARD -> GameSettings(50, 35, 90,45)
        }
    }
}