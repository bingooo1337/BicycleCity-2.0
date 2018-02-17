package com.diploma.volodymyr.bicyclecity.presentation.view.navigation

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface MenuView : BaseView {
    fun openSignIn()
    fun openNews()
    fun openGroupRides()
    fun openCompetitions()
    fun openMap()
    fun openSettings()
    fun setTitle(title: String)
}
