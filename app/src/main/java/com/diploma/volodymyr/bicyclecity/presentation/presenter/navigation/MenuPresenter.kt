package com.diploma.volodymyr.bicyclecity.presentation.presenter.navigation

import android.util.Log
import android.view.MenuItem
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.request.DirectionOriginRequest
import com.diploma.volodymyr.bicyclecity.presentation.view.navigation.MenuView
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.App
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

@InjectViewState
class MenuPresenter : BasePresenter<MenuView>(), IMenuPresenter {

    @Inject
    lateinit var googleDirection: DirectionOriginRequest
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        firebaseAuth = FirebaseAuth.getInstance()
        googleDirection = App.instance.getGoogleMapsComponent().getGoogleDirections()
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
        googleDirection
                .from(LatLng(50.476898, 30.618307))
                .to(LatLng(50.448135, 30.452717))
                .transportMode(TransportMode.DRIVING)
                .execute(object: DirectionCallback {
                    override fun onDirectionSuccess(direction: Direction, rawBody: String) {
                        Log.e(this::class.java.simpleName, direction.routeList[0].legList[0].distance.value)
                        Log.e(this::class.java.simpleName, direction.routeList[0].legList[0].duration.value)
                    }

                    override fun onDirectionFailure(t: Throwable) {
                    }

                })
    }
}
