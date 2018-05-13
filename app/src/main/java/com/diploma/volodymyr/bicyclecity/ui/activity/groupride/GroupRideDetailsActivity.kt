package com.diploma.volodymyr.bicyclecity.ui.activity.groupride

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.*
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.data.objects.User
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl.GroupRideDetailsPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.groupride.GroupRideDetailsView
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_group_ride_details.*
import kotlinx.android.synthetic.main.content_group_ride_details.*
import kotlin.math.roundToInt

class GroupRideDetailsActivity : BaseActivity(), GroupRideDetailsView, OnMapReadyCallback {

    companion object {
        private const val GROUP_RIDE_TITLE = "group_ride_title"
        private const val GROUP_RIDE_ID = "group_ride_id"

        fun getIntent(context: Context, groupRideTitle: String, groupRideId: String) =
                Intent(context, GroupRideDetailsActivity::class.java)
                        .apply {
                            putExtra(GROUP_RIDE_TITLE, groupRideTitle)
                            putExtra(GROUP_RIDE_ID, groupRideId)
                        }
    }

    @InjectPresenter
    lateinit var presenter: GroupRideDetailsPresenter
    private lateinit var map: GoogleMap
    private lateinit var groupRideId: String

    @ProvidePresenter
    fun providePresenter() = GroupRideDetailsPresenter(groupRideId)

    override fun onCreate(savedInstanceState: Bundle?) {
        groupRideId = intent.getStringExtra(GROUP_RIDE_ID)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_ride_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = intent.getStringExtra(GROUP_RIDE_TITLE)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.group_ride_details_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fab.setOnClickListener { presenter.onJoinClicked() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    override fun showLoading() {
        group_ride_details_progress.setVisible()
    }

    override fun hideLoading() {
        group_ride_details_progress.setInvisible()
    }

    override fun enableJoinButton() {
        fab.setVisible()
        fab.isEnabled = true
    }

    override fun disableJoinButton() {
        fab.isEnabled = false
    }

    override fun showDoneButton() {
        disableJoinButton()
        fab.setVisible()
        fab.setImageDrawable(getDrawable(R.drawable.ic_done))
    }

    override fun showGroupRideDetails(groupRide: GroupRide) {
        with(groupRide) {
            description_tv.text = description
            date_tv.text = date?.getFormattedDateString()
            time_tv.text = date?.getTimeString()
            distance_tv.text = getString(R.string.km_placeholder, (distance / 100).roundToInt() / 10.0)
            approximate_time_tv.text = getString(R.string.mins_placeholder, approximateTime)
        }
    }

    override fun showCreatorInfo(user: User) {
        creator_name_tv.text = getString(R.string.user_full_name_placeholder, user.firstName, user.lastName)
        creator_email_tv.text = user.email
        creator_number_tv.text = user.mobileNumber
    }

    override fun showRoute(start: LatLng, finish: LatLng, polyline: PolylineOptions) {
        map.addMarker(MarkerOptions().position(start)
                .title("Start")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(finish)
                .title("Finish")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
        map.addPolyline(polyline)

        val bounds = LatLngBounds.Builder().include(start).include(finish).build()
        val displayWidth = windowManager.getDisplayWidth()
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, displayWidth, displayWidth, 100))
    }

    override fun showUsersList(userNames: String) {
        riders_list_tv.text = userNames
    }
}
