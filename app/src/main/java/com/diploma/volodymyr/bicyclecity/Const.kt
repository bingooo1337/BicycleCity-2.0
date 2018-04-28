package com.diploma.volodymyr.bicyclecity

import java.text.SimpleDateFormat
import java.util.*

object Const {
    // Database
    const val USERS = "users"
    const val GROUP_RIDES = "group_rides"
    const val MARKERS = "markers"

    // Menu Fragments
    const val NEWS_FRAGMENT = "news_fragment"
    const val GROUP_RIDES_FRAGMENT = "group_rides_fragment"
    const val COMPETITIONS_FRAGMENT = "competitions_fragment"
    const val MAP_FRAGMENT = "map_fragment"
    const val SETTINGS_FRAGMENT = "settings_fragment"

    val GROUP_RIDE_DATEFORMAT = SimpleDateFormat("HH:mm 'on' MMMM d", Locale.getDefault())
}