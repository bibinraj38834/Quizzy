package com.project.quizzy.data.repo

import com.project.quizzy.data.model.Question
import com.project.quizzy.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class QuizRepository {
    fun fetchQuestions(): Flow<List<Question>> = flow {
        val response = RetrofitInstance.api.getQuestions()
        emit(response)
    }.catch { e ->
        e.printStackTrace()
        emit(emptyList()) // fallback
    }.flowOn(Dispatchers.IO)
}