package com.singularitycoder.swapwithme

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    var name: String = "",
    var mobileNumber: String = "",
    var dateAdded: Long = 0,
    var imagePath: String = "",
    var rating: Float = 0f,
    var ratingCount: Int = 0,
    var location: String = "",
    var sellerName: String = "",
    @DrawableRes var tempImageDrawable: Int = -1
) : Parcelable