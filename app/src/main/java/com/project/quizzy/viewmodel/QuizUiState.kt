package com.project.quizzy.viewmodel

import com.project.quizzy.data.model.Question

sealed class QuizUiState {
    object Loading : QuizUiState()

    data class Success(
        val questions: List<Question>,
        val currentIndex: Int = 0,
        val score: Int = 0,
        val streak: Int = 0,
        val longestStreak: Int = 0,
        val skipped: Int = 0
    ) : QuizUiState()

    data class Error(val message: String) : QuizUiState()
}
