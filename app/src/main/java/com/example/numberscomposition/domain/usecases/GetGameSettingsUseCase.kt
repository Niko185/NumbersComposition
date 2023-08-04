package com.example.numberscomposition.domain.usecases

import com.example.numberscomposition.domain.models.GameSettings
import com.example.numberscomposition.domain.models.Level
import com.example.numberscomposition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level) : GameSettings {
        return repository.getGameSettings(level)
    }
}