package com.diploma.volodymyr.bicyclecity.data.objects

import android.os.Parcelable
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class GroupRide(var title: String = "",
                     var description: String = "",
                     var date: Date? = null,
                     var start: @RawValue GeoPoint? = null,
                     var finish: @RawValue GeoPoint? = null,
                     var avgSpeed: Int = -1,
                     var distance: Double = 0.0,
                     var approximateTime: Long = 0,
                     var users: ArrayList<User>? = null) : Parcelable