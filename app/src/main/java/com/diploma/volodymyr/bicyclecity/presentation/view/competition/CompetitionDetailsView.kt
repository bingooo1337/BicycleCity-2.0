package com.diploma.volodymyr.bicyclecity.presentation.view.competition

import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView

interface CompetitionDetailsView : BaseView {
    fun showCompetitionDetails(competition: Competition)
    fun startTrackingLocation(competitionId: String)
}