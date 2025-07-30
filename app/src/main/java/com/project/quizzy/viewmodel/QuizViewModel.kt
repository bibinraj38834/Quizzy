package com.project.quizzy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.quizzy.data.repo.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repo: QuizRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<QuizUiState>(QuizUiState.Loading)
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun loadQuestions() {
        viewModelScope.launch {
            repo.fetchQuestions()
                .onStart { _uiState.value = QuizUiState.Loading }
                .catch { e ->
                    _uiState.value = QuizUiState.Error("Failed: ${e.localizedMessage}")
                }
                .collect { data ->
                    if (data.isEmpty()) {
                        _uiState.value = QuizUiState.Error("No questions available")
                    } else {
                        _uiState.value = QuizUiState.Success(questions = data)
                        println(data)
                    }
                }
        }
    }

    fun answer(selected: Int) {
        val state = _uiState.value
        if (state is QuizUiState.Success) {
            val q = state.questions[state.currentIndex]
            var newScore = state.score
            var newStreak = state.streak
            var longest = state.longestStreak

            if (selected == q.correctOptionIndex) {
                newScore++
                newStreak++
                longest = max(longest, newStreak)
            } else {
                newStreak = 0
            }

            _uiState.value = state.copy(score = newScore, streak = newStreak, longestStreak = longest)
        }
    }

    fun skip() {
        val state = _uiState.value
        if (state is QuizUiState.Success) {
            _uiState.value = state.copy(skipped = state.skipped + 1)
        }
    }

    fun next() {
        val state = _uiState.value
        if (state is QuizUiState.Success) {
            _uiState.value = state.copy(currentIndex = state.currentIndex + 1)
        }
    }
}
