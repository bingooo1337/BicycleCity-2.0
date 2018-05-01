package com.diploma.volodymyr.bicyclecity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.diploma.volodymyr.bicyclecity.Const.DEFAULT_DATE_FORMAT
import com.diploma.volodymyr.bicyclecity.Const.DEFAULT_TIME_FORMAT
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.GeoPoint
import java.text.SimpleDateFormat
import java.util.*

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showShortToast(stringId: Int) {
    App.instance.getString(stringId)?.let {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showLongToast(stringId: Int) {
    App.instance.getString(stringId)?.let {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }
}

fun ViewGroup.inflate(layoutRes: Int) =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

fun LatLng.getGeoPoint() = GeoPoint(this.latitude, this.longitude)

fun GeoPoint.getLatLng() = LatLng(this.latitude, this.longitude)

fun View.setVisible() {
    if (this.visibility != View.VISIBLE)
        visibility = View.VISIBLE
}

fun View.setInvisible() {
    if (this.visibility != View.INVISIBLE)
        visibility = View.INVISIBLE
}

fun View.setGone() {
    if (this.visibility != View.GONE)
        visibility = View.GONE
}

fun Date.getFormattedDateString(): String = DEFAULT_DATE_FORMAT.format(this)

fun Date.getTimeString(): String = DEFAULT_TIME_FORMAT.format(this)

fun <TResult> Task<TResult>.subscribe(successListener: (TResult) -> Unit,
                                      failureListener: (Exception) -> Unit) {
    addOnSuccessListener { successListener.invoke(result) }
    addOnFailureListener { failureListener.invoke(it) }
}