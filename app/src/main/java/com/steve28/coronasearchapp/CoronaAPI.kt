package com.steve28.coronasearchapp

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface CoronaAPI {
    @GET("country/{region}")
    fun getDatas(@Path("region") keyword: String): Observable<List<CoronaData>>
}