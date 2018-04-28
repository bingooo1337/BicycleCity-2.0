package com.diploma.volodymyr.bicyclecity.data.objects

import com.google.firebase.firestore.GeoPoint

data class Marker(var name: String = "",
                  var desc: String = "",
                  var geo: GeoPoint = GeoPoint(-1.0, -1.0),
                  var type: String = "")