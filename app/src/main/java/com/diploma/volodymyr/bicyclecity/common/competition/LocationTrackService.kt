package com.diploma.volodymyr.bicyclecity.common.competition

import com.diploma.volodymyr.bicyclecity.common.App
import com.diploma.volodymyr.bicyclecity.model.CompetitionRepository

class LocationTrackService(private val competitionId: String) {

    init {
        App.INSTANSE.getDataComponent().inject(this)
    }

    lateinit var repository: CompetitionRepository

    fun startTracking() {}

    fun stopTracking() {}
}