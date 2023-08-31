package com.ramazantiftik.koincrptocrazy.service

import com.ramazantiftik.koincrptocrazy.model.Crypto
import com.ramazantiftik.koincrptocrazy.util.Constans.API_KEY
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitAPI {

    @GET(API_KEY)
    suspend fun getCryptos() : Response<List<Crypto>>

}