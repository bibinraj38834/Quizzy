package com.project.quizzy.network

import com.project.quizzy.data.model.Question
import retrofit2.http.GET

interface QuizApiService {
    @GET(".")
    suspend fun getQuestions(): List<Question>
}