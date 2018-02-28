package com.diploma.volodymyr.bicyclecity.data

import com.google.android.gms.maps.model.LatLng
import java.util.*
import kotlin.collections.ArrayList

class GroupRide(var title: String = "",
                var description: String = "",
                var date: Date? = null,
                var start: LatLng? = null,
                var finish: LatLng? = null,
                var users: List<User> = ArrayList())