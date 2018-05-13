package com.diploma.volodymyr.bicyclecity.presentation.view.groupride

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface CreateGroupRideView : BaseView {
    fun showDatePicker()
    fun showTimePicker()
    fun hidePickers()
    fun setDate(date: String)
    fun setTime(time: String)
    fun setErrorHints(titleError: String, descError: String)
    @StateStrategyType(SkipStrategy::class)
    fun goToNextScreen(groupRide: GroupRide)
}