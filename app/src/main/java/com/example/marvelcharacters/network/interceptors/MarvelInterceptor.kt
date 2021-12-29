package com.example.marvelcharacters.network.interceptors

import com.example.marvelcharacters.BuildConfig
import com.example.marvelcharacters.utils.toMD5
import okhttp3.Interceptor
import okhttp3.Response

class MarvelInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val timeStamp = System.currentTimeMillis().toString()
        val hash = (timeStamp + BuildConfig.privateApiKey + BuildConfig.publicApiKey).toMD5()

        var original = chain.request()

        val url = chain.request().url
            .newBuilder()
            .addQueryParameter("ts", timeStamp)
            .addQueryParameter("apikey", BuildConfig.publicApiKey)
            .addQueryParameter("hash", hash)
            .build()

        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}