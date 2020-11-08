package com.steve28.coronasearchapp

import dagger.Component

@Component
interface CoronaClientComponent {

    fun getCoronaClient(): CoronaClient

    fun inject(activity: MainActivity)
}