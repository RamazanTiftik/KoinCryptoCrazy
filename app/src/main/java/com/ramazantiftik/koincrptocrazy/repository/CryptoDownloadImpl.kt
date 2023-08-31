package com.ramazantiftik.koincrptocrazy.repository

import com.ramazantiftik.koincrptocrazy.model.Crypto
import com.ramazantiftik.koincrptocrazy.service.RetrofitAPI
import com.ramazantiftik.koincrptocrazy.util.Resource

class CryptoDownloadImpl (private val api : RetrofitAPI) : CryptoDownload {

    override suspend fun downloadCryptos(): Resource<List<Crypto>> {

        return try {
            val response=api.getCryptos()
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(null,"Error!")
            } else {
                Resource.error(null, "Error!")
            }
        } catch (e: Exception){
            Resource.error(null,"Error!")
        }

    }

}