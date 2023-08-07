package com.example.numberscomposition.presentation.fragments

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.numberscomposition.R
import com.example.numberscomposition.data.GameRepositoryImpl
import com.example.numberscomposition.domain.models.GameResult
import com.example.numberscomposition.domain.models.GameSettings
import com.example.numberscomposition.domain.models.Level
import com.example.numberscomposition.domain.models.Question
import com.example.numberscomposition.domain.usecases.GenerateQuestionUseCase
import com.example.numberscomposition.domain.usecases.GetGameSettingsUseCase


class GameViewModel(private val application: Application, private val level: Level) : ViewModel() {

    private lateinit var gameSettings: GameSettings

    private val repository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private var timer: CountDownTimer? = null

    private var countRightAnswers = 0
    private var countQuestions = 0

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String> // Эта переменная будет иметь значение _formattedTime
        get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String> // Эта переменная будет иметь значение _formattedTime
        get() = _progressAnswers

    private val _percentRightAnswers = MutableLiveData<Int>()
    val percentRightAnswers: LiveData<Int>
        get() = _percentRightAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    init{
        startGame()
    }

    fun startGame() {
        getGameSettingsForStartGame(level)
        startTimer()
        generatedQuestion()
        updateProgress()
   }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generatedQuestion()
    }

    private fun updateProgress() {
        val percent = calculatePercentRightAnswers()
        _percentRightAnswers.value = percent
        _progressAnswers.value = String.format(
            application.resources.getString(R.string.progress_answers),
            countRightAnswers,
            gameSettings.minCountAnswersForWinner
        )
        _enoughCount.value = countRightAnswers >= gameSettings.minCountAnswersForWinner
        _enoughPercent.value = percent >= gameSettings.minPercentAnswersForWinner
    }

    private fun calculatePercentRightAnswers() : Int {
        if(countQuestions == 0){
            return 0
        }
        return ((countRightAnswers / countQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countRightAnswers++
        }
        countQuestions++
    }

    private fun getGameSettingsForStartGame(level: Level) {
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentAnswersForWinner
    }

    private fun startTimer(){
        timer = object : CountDownTimer(
            gameSettings.timeGameInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(milliseconds: Long) {
                _formattedTime.value = formatTime(milliseconds)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun generatedQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }


    private fun finishGame() {
        _gameResult.value = GameResult(
            isWinner = enoughCount.value == true && enoughPercent.value == true,
            countRightAnswers = countRightAnswers,
            countAllQuestions = countQuestions,
            gameSettings = gameSettings
        )
    }



    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60


    }
}