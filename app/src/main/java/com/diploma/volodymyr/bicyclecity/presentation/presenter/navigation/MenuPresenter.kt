package com.diploma.volodymyr.bicyclecity.presentation.presenter.navigation

import android.view.MenuItem
import com.diploma.volodymyr.bicyclecity.presentation.view.navigation.MenuView
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.common.Const.COMPETITIONS_FRAGMENT
import com.diploma.volodymyr.bicyclecity.common.Const.GROUP_RIDES_FRAGMENT
import com.diploma.volodymyr.bicyclecity.common.Const.MAP_FRAGMENT
import com.diploma.volodymyr.bicyclecity.common.Const.NEWS_FRAGMENT
import com.diploma.volodymyr.bicyclecity.common.Const.SETTINGS_FRAGMENT
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.ui.fragment.*
import com.google.firebase.auth.FirebaseAuth

@InjectViewState
class MenuPresenter : BasePresenter<MenuView>(), IMenuPresenter {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.currentUser?.let { viewState.showUserNameEmail(it.displayName!!, it.email!!) }
    }

    override fun menuItemClicked(item: MenuItem) {
        if (item.itemId == R.id.nav_logout) {
            firebaseAuth.signOut()
            viewState.openSignIn()
        } else {
            viewState.setTitle(item.title.toString())
            val fragmentTag: String
            val fragment = when (item.itemId) {
                R.id.nav_news -> {
                    fragmentTag = NEWS_FRAGMENT
                    NewsFragment()
                }
                R.id.nav_group -> {
                    fragmentTag = GROUP_RIDES_FRAGMENT
                    GroupRidesFragment()
                }
                R.id.nav_competition -> {
                    fragmentTag = COMPETITIONS_FRAGMENT
                    CompetitionsFragment()
                }
                R.id.nav_map -> {
                    fragmentTag = MAP_FRAGMENT
                    MapFragment()
                }
                R.id.nav_settings -> {
                    fragmentTag = SETTINGS_FRAGMENT
                    SettingsFragment()
                }
                else -> {
                    fragmentTag = NEWS_FRAGMENT
                    NewsFragment()
                }
            }
            viewState.openFragment(fragment, fragmentTag)
        }
    }

    override fun optionsItemClicked(id: Int) {
    }
}
