package com.diploma.volodymyr.bicyclecity.presentation.view.auth

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface SignInView : BaseView {

    @StateStrategyType(SingleStateStrategy::class)
    fun showLoading()

    fun showLoginError(error: String)
    fun showPasswordError(error: String)
    fun hideLoading()
    fun startSignUp()
    fun openApp()
}
