package com.ramazantiftik.koincrptocrazy.repository

import com.ramazantiftik.koincrptocrazy.model.Crypto
import com.ramazantiftik.koincrptocrazy.util.Resource

interface CryptoDownload {

    suspend fun downloadCryptos() : Resource<List<Crypto>>

}