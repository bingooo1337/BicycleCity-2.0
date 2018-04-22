package com.diploma.volodymyr.bicyclecity.data.objects

import com.google.firebase.firestore.GeoPoint
import java.util.*
import kotlin.collections.ArrayList

data class GroupRide(var title: String = "",
                     var description: String = "",
                     var date: Date? = null,
                     var start: GeoPoint? = null,
                     var finish: GeoPoint? = null,
                     var distance: Double = 0.0,
                     var approximateTime: Long = 0,
                     var users: ArrayList<User>? = null)