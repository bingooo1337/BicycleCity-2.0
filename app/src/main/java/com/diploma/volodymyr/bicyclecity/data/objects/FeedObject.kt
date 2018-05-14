package com.diploma.volodymyr.bicyclecity.data.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class FeedObject(
        var title: String = "",
        var description: String = "",
        var image: String = "",
        var date: Date = Date()
) : Parcelable