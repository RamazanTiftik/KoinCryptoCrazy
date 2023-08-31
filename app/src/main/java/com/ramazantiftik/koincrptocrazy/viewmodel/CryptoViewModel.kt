package com.ramazantiftik.koincrptocrazy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramazantiftik.koincrptocrazy.model.Crypto
import com.ramazantiftik.koincrptocrazy.repository.CryptoDownload
import com.ramazantiftik.koincrptocrazy.service.RetrofitAPI
import com.ramazantiftik.koincrptocrazy.util.Constans
import com.ramazantiftik.koincrptocrazy.util.Resource
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoViewModel (
    private val cryptoDownloadRepository: CryptoDownload
) : ViewModel(){

    //coroutine
    private var job: Job?= null
    val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
        cryptoError.value= Resource.error(true, throwable.localizedMessage)
    }

    val cryptoList= MutableLiveData<Resource<List<Crypto>>>()
    val cryptoError= MutableLiveData<Resource<Boolean>>()
    val cryptoLoading= MutableLiveData<Resource<Boolean>>()


    fun getDataFromAPI() {
        cryptoLoading.value=Resource.loading(true)

        //Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(Constans.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitAPI::class.java)


        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = cryptoDownloadRepository.downloadCryptos()

            withContext(Dispatchers.Main) {

                cryptoList.value=response
                cryptoLoading.value=Resource.loading(false)
                cryptoError.value=Resource.error(false, "")

            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}