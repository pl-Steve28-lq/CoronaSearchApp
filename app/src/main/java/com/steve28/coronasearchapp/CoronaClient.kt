package com.steve28.coronasearchapp

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class CoronaClient {

    @Inject
    constructor () {}

    fun getAPI() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("https://api.covid19api.com")
        .build()
        .create(CoronaAPI::class.java)
}