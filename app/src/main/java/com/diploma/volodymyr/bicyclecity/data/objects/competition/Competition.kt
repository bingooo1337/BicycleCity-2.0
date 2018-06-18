package com.diploma.volodymyr.bicyclecity.data.objects.competition

import android.os.Parcelable
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*

@Parcelize
data class Competition(
        var id: String = "",
        var title: String = "",
        var description: String = "",
        var isPrivate: Boolean = false,
        var accessCode: String? = null,
        var date: Date? = null,
        var prize: String? = null,
        var creatorId: String = "",
        var start: @RawValue GeoPoint? = null,
        var finish: @RawValue GeoPoint? = null,
        var encodedRoute: String = "",
        var distance: Int = -1,
        var bicycleType: BicycleType = BicycleType.ROAD,
        var trainingLevel: TrainingLevel = TrainingLevel.BEGINNER,
        var users: ArrayList<String> = arrayListOf()
) : Parcelable