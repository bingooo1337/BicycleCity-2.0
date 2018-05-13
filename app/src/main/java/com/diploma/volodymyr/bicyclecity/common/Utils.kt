package com.diploma.volodymyr.bicyclecity.common

import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.akexorcist.googledirection.util.DirectionConverter
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.Const.DEFAULT_DATE_FORMAT
import com.diploma.volodymyr.bicyclecity.common.Const.DEFAULT_TIME_FORMAT
import com.diploma.volodymyr.bicyclecity.common.Const.NetworkCalls.API_KEY
import com.diploma.volodymyr.bicyclecity.common.Const.NetworkCalls.GOOGLE_STATIC_MAP
import com.diploma.volodymyr.bicyclecity.common.Const.NetworkCalls.MARKER_FINISH
import com.diploma.volodymyr.bicyclecity.common.Const.NetworkCalls.MARKER_START
import com.diploma.volodymyr.bicyclecity.common.Const.NetworkCalls.POLYLINE_PATH_SETTINGS
import com.diploma.volodymyr.bicyclecity.common.Const.POLYLINE_WIDTH
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.GeoPoint
import java.util.*
import kotlin.collections.ArrayList

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

fun <TResult> Task<TResult>.subscribe(successListener: (TResult?) -> Unit,
                                      failureListener: (Exception) -> Unit) {
    addOnSuccessListener { successListener.invoke(result) }
    addOnFailureListener { failureListener.invoke(it) }
}

fun GroupRide.getStaticMapPath() =
        GOOGLE_STATIC_MAP +
                MARKER_START + "${start?.latitude},${start?.longitude}&" +
                MARKER_FINISH + "${finish?.latitude},${finish?.longitude}&" +
                POLYLINE_PATH_SETTINGS + "$encodedRoute&" +
                API_KEY + App.instance.getString(R.string.google_maps_key)

fun List<LatLng>.getPolyLineOptions(): PolylineOptions = DirectionConverter
        .createPolyline(App.instance, ArrayList(this), POLYLINE_WIDTH, Color.RED)

fun WindowManager.getDisplayWidth(): Int {
    val metrics = DisplayMetrics()
    defaultDisplay.getMetrics(metrics)
    return metrics.widthPixels
}