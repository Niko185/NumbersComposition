package com.example.numberscomposition.domain.models

data class Question(
    val sum: Int,
    val visibleNumber: Int,
    val answersVariants: List<Int>
)