package com.steve28.coronasearchapp

import com.google.gson.annotations.SerializedName

data class CoronaData (
    @SerializedName("Confirmed") val confirmed: Int,
    @SerializedName("Deaths") val deaths: Int,
    @SerializedName("Recovered") val recovered: Int,
    @SerializedName("Date") val date: String,
)