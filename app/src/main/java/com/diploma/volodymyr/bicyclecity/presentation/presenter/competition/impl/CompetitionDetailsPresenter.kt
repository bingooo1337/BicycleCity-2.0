package com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.impl

import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.common.competition.LocationTrackService
import com.diploma.volodymyr.bicyclecity.common.competition.ResultCalculator
import com.diploma.volodymyr.bicyclecity.model.CompetitionRepository
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.ICompetitionDetailsPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.competition.CompetitionDetailsView
import javax.inject.Inject

@InjectViewState
class CompetitionDetailsPresenter(private val competitionId: String) :
        BasePresenter<CompetitionDetailsView>(), ICompetitionDetailsPresenter {

    init {
        dataComponent.inject(this)
    }

    @Inject
    lateinit var repository: CompetitionRepository

    private val trackService = LocationTrackService(competitionId)
    private val resultCalculator = ResultCalculator(mapOf())

    override fun onRefreshSwiped() {
        repository.getCompetitionById(competitionId)
                .addSnapshotListener { snapshot, ex ->

                }
    }
}