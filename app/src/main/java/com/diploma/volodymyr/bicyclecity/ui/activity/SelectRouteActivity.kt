package com.diploma.volodymyr.bicyclecity.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.getDisplayWidth
import com.diploma.volodymyr.bicyclecity.common.setInvisible
import com.diploma.volodymyr.bicyclecity.common.setVisible
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.presentation.presenter.impl.SelectRoutePresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.SelectRouteView
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseActivity
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.location.places.ui.PlacePicker.getPlace
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_create_group_ride_route.*

class SelectRouteActivity : BaseActivity(), SelectRouteView, OnMapReadyCallback {

    companion object {
        private val TAG = SelectRouteActivity::class.java.simpleName
        private const val REQUEST_START_PLACE_PICKER = 1
        private const val REQUEST_FINISH_PLACE_PICKER = 2
        private const val GROUP_RIDE = "group_ride"
        private const val COMPETITION = "competition"
        fun getIntent(context: Context, groupRide: GroupRide): Intent =
                Intent(context, SelectRouteActivity::class.java)
                        .putExtra(GROUP_RIDE, groupRide)

        fun getIntent(context: Context, competition: Competition): Intent =
                Intent(context, SelectRouteActivity::class.java)
                        .putExtra(COMPETITION, competition)
    }

    @InjectPresenter
    lateinit var presenter: SelectRoutePresenter

    private lateinit var map: GoogleMap
    private var markerStart: Marker? = null
    private var markerFinish: Marker? = null
    private var polyline: Polyline? = null

    private var groupRide: GroupRide? = null
    private var competition: Competition? = null

    @ProvidePresenter
    fun providePresenter() =
            if (groupRide == null) competition?.let { SelectRoutePresenter(it) }
            else groupRide?.let { SelectRoutePresenter(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        groupRide = intent.getParcelableExtra(GROUP_RIDE)
        competition = intent.getParcelableExtra(COMPETITION)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group_ride_route)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_group_ride_route_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.create_group_ride -> {
                presenter.onCreateClicked()
            }
            android.R.id.home -> finish()
        }
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    override fun showLoading() {
        choose_route_progress_bar.setVisible()
    }

    override fun hideLoading() {
        choose_route_progress_bar.setInvisible()
    }

    override fun addMarker(coordinates: LatLng, pointName: String, isStart: Boolean) {
        if (isStart) {
            markerStart?.remove()
            markerStart = map.addMarker(MarkerOptions()
                    .position(coordinates)
                    .title(getString(R.string.start))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))

            choose_start.setText(pointName)
            choose_start_layout.hint = getString(R.string.start_point)
        } else {
            markerFinish?.remove()
            markerFinish = map.addMarker(MarkerOptions()
                    .position(coordinates)
                    .title(getString(R.string.finish))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )
            choose_finish.setText(pointName)
            choose_finish_layout.hint = getString(R.string.finish_point)
        }
        if (markerStart != null && markerFinish != null) {
            val bounds = LatLngBounds.Builder()
                    .include(markerStart!!.position)
                    .include(markerFinish!!.position)
                    .build()
            val displayWidth = windowManager.getDisplayWidth()
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, displayWidth, displayWidth, 100))
        } else {
            map.animateCamera(CameraUpdateFactory
                    .newCameraPosition(CameraPosition.fromLatLngZoom(coordinates, 10F)))
        }
    }

    override fun drawRoute(polyline: PolylineOptions) {
        this.polyline?.remove()
        this.polyline = map.addPolyline(polyline)
    }

    override fun activateChoosingPoints() {
        choose_start.isEnabled = true
        choose_finish.isEnabled = true
    }

    override fun deactivateChoosingPoints() {
        choose_start.isEnabled = false
        choose_finish.isEnabled = false
    }

    override fun openChoosingStart() {
        choosePointWithCode(REQUEST_START_PLACE_PICKER)
    }

    override fun openChoosingFinish() {
        choosePointWithCode(REQUEST_FINISH_PLACE_PICKER)
    }

    override fun closeScreenWithResult() {
        setResult(RESULT_OK)
        finish()
    }

    override fun setErrorHints(startError: String, finishError: String) {
        choose_start_layout.error = startError
        choose_finish_layout.error = finishError
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ((requestCode == REQUEST_START_PLACE_PICKER || requestCode == REQUEST_FINISH_PLACE_PICKER)
                && resultCode == Activity.RESULT_OK) {
            val place = getPlace(this, data)

            if (requestCode == REQUEST_START_PLACE_PICKER)
                presenter.startPointChosen(place)
            else
                presenter.finishPointChosen(place)
        } else {
            presenter.nothingChosen()
        }
    }

    private fun initView() {
        choose_start.setOnClickListener { presenter.startPointClicked() }
        choose_finish.setOnClickListener { presenter.finishPointClicked() }
    }

    private fun choosePointWithCode(code: Int) {
        try {
            val intentBuilder = PlacePicker.IntentBuilder()
            startActivityForResult(intentBuilder.build(this), code)
        } catch (e: GooglePlayServicesNotAvailableException) {
            Log.e(TAG, e.message)
            e.printStackTrace()
        }
    }
}
