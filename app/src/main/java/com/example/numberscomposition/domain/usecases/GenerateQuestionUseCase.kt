package com.example.numberscomposition.domain.usecases

import com.example.numberscomposition.domain.models.GameSettings
import com.example.numberscomposition.domain.models.Question
import com.example.numberscomposition.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {

    operator fun invoke(maxSumValue: Int, ): Question {
        return repository.generateQuestion(maxSumValue, COUNT_ALL_ANSWERS)
    }

    private companion object {
        const val COUNT_ALL_ANSWERS = 6
    }
}