package com.diploma.volodymyr.bicyclecity.ui.activity.base

import com.arellomobile.mvp.MvpAppCompatFragment
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView
import com.diploma.volodymyr.bicyclecity.showShortToast

class BaseFragment : MvpAppCompatFragment(), BaseView {
    override fun showToastMessage(message: String) {
        activity?.showShortToast(message)
    }
}