package com.android.foodappdemo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FoodItem(
    val id: Int,
    val categoryId: Int,
    val image: String?,
    val name: String?,
    val description: String?,
    val price: String,
    val weight: String?,
    val quantity: String?,
    val diameter: String?,
    val isNonVeg: Boolean?
) : Parcelable