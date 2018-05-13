package com.diploma.volodymyr.bicyclecity.presentation.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface BaseView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showToastMessage(message: String)

    @StateStrategyType(SkipStrategy::class)
    fun showToastMessage(stringId: Int)
}