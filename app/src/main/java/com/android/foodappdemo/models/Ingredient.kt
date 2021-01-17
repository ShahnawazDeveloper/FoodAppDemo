package com.android.foodappdemo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Ingredient(
    val id: Int,
    val name: String,
) : Parcelable