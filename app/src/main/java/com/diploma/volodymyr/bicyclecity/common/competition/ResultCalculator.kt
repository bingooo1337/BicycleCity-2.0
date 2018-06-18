package com.diploma.volodymyr.bicyclecity.common.competition

import com.diploma.volodymyr.bicyclecity.common.App
import com.diploma.volodymyr.bicyclecity.data.objects.competition.CompetitionResult
import com.diploma.volodymyr.bicyclecity.model.CompetitionRepository
import com.google.firebase.firestore.GeoPoint
import java.util.*
import javax.inject.Inject

class ResultCalculator(private val locationTracks: Map<String, Map<GeoPoint, Date>>) {

    init {
        App.INSTANSE.getDataComponent().inject(this)
    }

    @Inject
    lateinit var competitionRepository: CompetitionRepository

    fun calculateResulst(): List<CompetitionResult> {
        return listOf(CompetitionResult())
    }
}