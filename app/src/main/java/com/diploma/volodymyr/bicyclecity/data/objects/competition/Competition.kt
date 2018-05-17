package com.diploma.volodymyr.bicyclecity.data.objects.competition

import com.google.firebase.firestore.GeoPoint
import kotlinx.android.parcel.RawValue
import java.util.*

data class Competition(
        var id: String = "",
        var title: String = "",
        var description: String = "",
        var isPrivate: Boolean = false,
        var accessCode: String? = null,
        var date: Date? = null,
        var creatorId: String = "",
        var start: @RawValue GeoPoint? = null,
        var finish: @RawValue GeoPoint? = null,
        var encodedRoute: String = "",
        var bicycleType: BicycleType = BicycleType.ROAD,
        var trainingLevel: TrainingLevel = TrainingLevel.NOOB,
        var distance: Double = 0.0,
        var users: ArrayList<String>? = arrayListOf()
)