package com.diploma.volodymyr.bicyclecity.common

import java.text.SimpleDateFormat
import java.util.*

object Const {
    // Database
    const val USERS = "users"
    const val GROUP_RIDES = "group_rides"
    const val MARKERS = "markers"
    const val NEWS = "news"
    const val EVENTS = "events"
    const val COMPETITIONS = "competitions"

    // Menu Fragments
    const val NEWS_FRAGMENT = "news_fragment"
    const val GROUP_RIDES_FRAGMENT = "group_rides_fragment"
    const val COMPETITIONS_FRAGMENT = "competitions_fragment"
    const val MAP_FRAGMENT = "map_fragment"

    // Date Formats
    val GROUP_RIDE_DATE_FORMAT: SimpleDateFormat
        get() = SimpleDateFormat("HH:mm 'on' MMMM d", Locale.getDefault())
    val DEFAULT_DATE_FORMAT: SimpleDateFormat
        get() = SimpleDateFormat("dd MMMM y", Locale.getDefault())
    val DEFAULT_TIME_FORMAT: SimpleDateFormat
        get() = SimpleDateFormat("HH:mm", Locale.getDefault())

    object NetworkCalls {
        private const val STATIC_MAPS_API_URL = "https://maps.googleapis.com/maps/api/staticmap?"
        private const val MAP_SIZE = "size=1280x720"
        private const val MARKERS = "markers="

        const val GOOGLE_STATIC_MAP = "$STATIC_MAPS_API_URL$MAP_SIZE&"
        const val MARKER_START = MARKERS + "color:green|label:S|"
        const val MARKER_FINISH = MARKERS + "color:red|label:F|"
        const val POLYLINE_PATH_SETTINGS = "path=color:0x000080|enc:"
        const val API_KEY = "key="
    }

    // Map
    const val POLYLINE_WIDTH = 8
    const val CAMERA_PADDING = 100
    const val CAMERA_ZOOM = 10F
}