package com.diploma.volodymyr.bicyclecity.presentation.view.navigation

import android.support.v4.app.Fragment
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface MenuView : BaseView {
    fun openSignIn()
    fun openFragment(fragment: Fragment, tag: String)
    fun setTitle(title: String)
    fun showUserNameEmail(name: String, email: String)
}
