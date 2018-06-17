package com.diploma.volodymyr.bicyclecity.dagger.component

import com.diploma.volodymyr.bicyclecity.common.competition.LocationTrackService
import com.diploma.volodymyr.bicyclecity.common.competition.ResultCalculator
import com.diploma.volodymyr.bicyclecity.dagger.module.RepositoryModule
import com.diploma.volodymyr.bicyclecity.presentation.presenter.auth.impl.SignInPresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.auth.impl.SignUpPresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.impl.CompetitionDetailsPresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.impl.CompetitionsListPresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl.GroupRideDetailsPresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl.GroupRidesListPresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.map.impl.MapPresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.news.impl.FeedPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface DataComponent {
    // Auth
    fun inject(presenter: SignInPresenter)

    fun inject(presenter: SignUpPresenter)

    // GroupRides
    fun inject(presenter: GroupRidesListPresenter)

    fun inject(presenter: GroupRideDetailsPresenter)

    // Competitions
    fun inject(presenter: CompetitionsListPresenter)

    fun inject(presenter: CompetitionDetailsPresenter)

    fun inject(presenter: ResultCalculator)

    fun inject(presenter: LocationTrackService)

    // Map
    fun inject(presenter: MapPresenter)

    // Feed
    fun inject(presenter: FeedPresenter)
}