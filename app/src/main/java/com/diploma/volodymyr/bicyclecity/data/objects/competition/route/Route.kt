package com.diploma.volodymyr.bicyclecity.data.objects.competition.route

import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.firestore.GeoPoint

class Route {
    val name: String = ""
    val path: List<GeoPoint> = listOf()
    val distance: Double = 0.0
    val polyline: PolylineOptions = PolylineOptions()
    val difficult: RouteDifficult = RouteDifficult.EASY
}