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

    // Date Formats
    val GROUP_RIDE_DATE_FORMAT = SimpleDateFormat("HH:mm 'on' MMMM d", Locale.getDefault())
    val DEFAULT_DATE_FORMAT = SimpleDateFormat("dd MMMM Y", Locale.getDefault())
    val DEFAULT_TIME_FORMAT = SimpleDateFormat("HH:mm", Locale.getDefault())
}