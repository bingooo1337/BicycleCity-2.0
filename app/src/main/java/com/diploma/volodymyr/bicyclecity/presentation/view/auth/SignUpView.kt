package com.diploma.volodymyr.bicyclecity.presentation.view.auth

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface SignUpView : BaseView {

    @StateStrategyType(SingleStateStrategy::class)
    fun showLoading()

    fun hideLoading()
    fun showFirstNameError(error: String)
    fun showLastNameError(error: String)
    fun showEmailError(error: String)
    fun showNumberError(error: String)
    fun showPasswordError(error: String)
    fun closeScreen()
}