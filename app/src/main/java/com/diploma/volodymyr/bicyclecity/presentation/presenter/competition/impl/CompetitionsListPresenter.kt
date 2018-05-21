package com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.impl

import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.model.CompetitionRepository
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.ICompetitionsListPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.competition.CompetitionsListView
import javax.inject.Inject

@InjectViewState
class CompetitionsListPresenter : BasePresenter<CompetitionsListView>(), ICompetitionsListPresenter {

    init {
        dataComponent.inject(this)
    }

    @Inject
    lateinit var repository: CompetitionRepository

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        onRefreshSwiped()
    }

    override fun onRefreshSwiped() {
        viewState.showLoading()

        repository.getCompetitions()
                .addSnapshotListener { snapshot, ex ->
                    viewState.hideLoading()

                    if (ex != null) {
                        viewState.showToastMessage(R.string.loading_failed)
                        ex.printStackTrace()
                        return@addSnapshotListener
                    }

                    snapshot?.let {
                        viewState.showCompetitions(it.toObjects(Competition::class.java))
                    }
                }
    }
}