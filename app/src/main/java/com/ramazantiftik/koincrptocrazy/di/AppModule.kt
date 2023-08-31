package com.ramazantiftik.koincrptocrazy.di

import com.ramazantiftik.koincrptocrazy.repository.CryptoDownload
import com.ramazantiftik.koincrptocrazy.repository.CryptoDownloadImpl
import com.ramazantiftik.koincrptocrazy.service.RetrofitAPI
import com.ramazantiftik.koincrptocrazy.util.Constans.BASE_URL
import com.ramazantiftik.koincrptocrazy.viewmodel.CryptoViewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule= module {

    //factory {  } -> everytime we inject -> new instance

    //singleton scope
    single {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitAPI::class.java)

    }


    //cryptoDownload ı gördüğü zaman CryptoDownloadImpl'ı inject eder
    single<CryptoDownload> {
        CryptoDownloadImpl(get()) // get -> üstte retrofit tanımlandığı için direkt ordan alıyor
    }

    single {
        CryptoViewModel(get())
    }

}