package com.diploma.volodymyr.bicyclecity.presentation.view.competition

import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView

interface CompetitionsListView: BaseView {
    fun showLoading()
    fun hideLoading()
    fun showCompetitions(competitions: List<Competition>)
}