package com.singularitycoder.swapwithme

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    var name: String = "",
    var mobileNumber: String = "",
    var dateStarted: Long = 0,
    var imagePath: String = "",
    var rating: Float = 0f,
    var ratingCount: Int = 0,
    var location: String = "",
    var profession: String = "",
    var hourlyWorth: Double = 0.0,
    var skillsList: List<String> = emptyList(),
    var servicesList: List<String> = emptyList(),
    var iCanAfford: String = "",
    @DrawableRes var tempImageDrawable: Int = -1
) : Parcelable

@Parcelize
data class PersonService(
    var title: String,
    var timeToComplete: Long
) : Parcelable
