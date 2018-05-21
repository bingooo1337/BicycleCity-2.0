package com.diploma.volodymyr.bicyclecity.presentation.view.competition

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView

interface CreateCompetitionView : BaseView {
    fun showDatePicker()
    fun showTimePicker()
    fun hidePickers()
    fun setDate(date: String)
    fun setTime(time: String)
    fun showPrivateCode(code: String)
    fun hidePrivateCode()
    fun showPrizeEditText()
    fun hidePrizeEditText()
    fun setErrorHints(titleError: String, descError: String)
    @StateStrategyType(SkipStrategy::class)
    fun goToNextScreen(competition: Competition)
}