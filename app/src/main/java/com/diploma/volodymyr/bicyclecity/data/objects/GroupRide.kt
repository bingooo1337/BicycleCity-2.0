package com.diploma.volodymyr.bicyclecity.data.objects

import android.os.Parcelable
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.App
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class GroupRide(var id: String = "",
                     var title: String = "",
                     var description: String = "",
                     var date: Date? = null,
                     var creatorId: String = "",
                     var start: @RawValue GeoPoint? = null,
                     var finish: @RawValue GeoPoint? = null,
                     var encodedRoute: String = "",
                     var avgSpeed: Int = -1,
                     var distance: Double = 0.0,
                     var approximateTime: Long = 0,
                     var users: ArrayList<String>? = arrayListOf()) : Parcelable