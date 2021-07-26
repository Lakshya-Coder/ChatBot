package com.lakshyagupta7089.chatbot

import com.lakshyagupta7089.chatbot.model.Message
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitAPI {
    @GET
    fun getMessage(@Url url: String): Call<Message>
}