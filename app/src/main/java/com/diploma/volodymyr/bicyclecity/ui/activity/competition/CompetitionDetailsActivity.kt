package com.diploma.volodymyr.bicyclecity.ui.activity.competition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.impl.CompetitionDetailsPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.competition.CompetitionDetailsView
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseActivity

class CompetitionDetailsActivity : BaseActivity(), CompetitionDetailsView {

    companion object {
        private const val COMPETITION_ID = "competition_id"

        fun getIntent(context: Context, competitionId: String) =
                Intent(context, CompetitionDetailsActivity::class.java)
                        .apply { putExtra(COMPETITION_ID, competitionId) }
    }

    @InjectPresenter
    lateinit var presenter: CompetitionDetailsPresenter
    private lateinit var competitionId: String

    @ProvidePresenter
    fun providePresenter() = CompetitionDetailsPresenter(competitionId)

    override fun onCreate(savedInstanceState: Bundle?) {
        competitionId = intent.getStringExtra(COMPETITION_ID)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competition_details)
    }

    override fun showCompetitionDetails(competition: Competition) {
    }

    override fun startTrackingLocation(competitionId: String) {
    }
}
