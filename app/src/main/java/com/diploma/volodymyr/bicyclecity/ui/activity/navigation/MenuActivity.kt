package com.diploma.volodymyr.bicyclecity.ui.activity.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.diploma.volodymyr.bicyclecity.presentation.view.navigation.MenuView
import com.diploma.volodymyr.bicyclecity.presentation.presenter.navigation.MenuPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.ui.activity.auth.SignUpActivity
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseActivity

class MenuActivity : BaseActivity(), MenuView {
    companion object {
        val TAG = SignUpActivity::class.java.simpleName
        fun getIntent(context: Context) = Intent(context, MenuActivity::class.java)
    }

    @InjectPresenter
    lateinit var mMenuPresenter: MenuPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }
}
