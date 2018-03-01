package com.diploma.volodymyr.bicyclecity.data

import com.google.firebase.firestore.GeoPoint
import java.util.*
import kotlin.collections.ArrayList

data class GroupRide(var title: String = "",
                var description: String = "",
                var date: Date? = null,
                var start: GeoPoint? = null,
                var finish: GeoPoint? = null,
                var users: ArrayList<User>? = null)