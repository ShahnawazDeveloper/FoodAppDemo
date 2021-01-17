package com.android.foodappdemo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.lang.StringBuilder

@Parcelize
data class FoodItem(
    val id: Int,
    val categoryId: Int,
    val image: String?,
    val name: String?,
    val ingredients:List<Ingredient>?,
    val description: String?,
    val price: String,
    val weight: String?,
    val quantity: String?,
    val diameter: String?,
    val isNonVeg: Boolean?
) : Parcelable