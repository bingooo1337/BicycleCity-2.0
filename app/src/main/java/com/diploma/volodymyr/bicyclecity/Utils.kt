package com.diploma.volodymyr.bicyclecity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ViewGroup.inflate(layoutRes: Int) =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

fun LatLng.getGeoPoint() = GeoPoint(this.latitude, this.longitude)