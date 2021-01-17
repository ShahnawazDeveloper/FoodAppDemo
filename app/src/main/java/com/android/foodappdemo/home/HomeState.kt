package com.android.foodappdemo.home

import com.airbnb.mvrx.*
import com.android.foodappdemo.models.Category

data class HomeState(
    val featuredImages: Async<List<String>> = Loading(),
    val category: Async<List<Category>> =  Loading()
) : MvRxState