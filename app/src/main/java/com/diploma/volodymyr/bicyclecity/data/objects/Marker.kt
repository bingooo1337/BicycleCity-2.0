package com.diploma.volodymyr.bicyclecity.data.objects

import com.google.firebase.firestore.GeoPoint

data class Marker(var title: String = "",
                  var desc: String = "",
                  var geo: GeoPoint = GeoPoint(0.0, 0.0),
                  var type: MarkerType = MarkerType.PARKING) {

    enum class MarkerType {
        RENT, WORKSHOP, PARKING
    }
}