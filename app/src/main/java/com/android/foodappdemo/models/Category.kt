package com.android.foodappdemo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val foodItems: List<FoodItem>
) : Parcelable