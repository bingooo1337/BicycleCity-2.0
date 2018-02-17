package com.diploma.volodymyr.bicyclecity.presentation.presenter.navigation

import android.view.MenuItem
import com.diploma.volodymyr.bicyclecity.presentation.view.navigation.MenuView
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.google.firebase.auth.FirebaseAuth

@InjectViewState
class MenuPresenter : BasePresenter<MenuView>(), IMenuPresenter {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun menuItemClicked(item: MenuItem) {
        viewState.setTitle(item.title.toString())
        when (item.itemId) {
            R.id.nav_news -> viewState.openNews()
            R.id.nav_group -> viewState.openGroupRides()
            R.id.nav_competition -> viewState.openCompetitions()
            R.id.nav_map -> viewState.openMap()
            R.id.nav_settings -> viewState.openSettings()
            R.id.nav_logout -> {
                firebaseAuth.signOut()
                viewState.openSignIn()
            }
        }
    }

    override fun optionsItemClicked(id: Int) {
    }
}
