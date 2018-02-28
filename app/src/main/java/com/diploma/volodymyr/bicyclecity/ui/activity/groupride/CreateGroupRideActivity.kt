package com.diploma.volodymyr.bicyclecity.ui.activity.groupride

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.diploma.volodymyr.bicyclecity.R
import kotlinx.android.synthetic.main.activity_create_group_ride.*
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.location.places.ui.PlacePicker
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl.CreateGroupRidePresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.groupride.CreateGroupRideView
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseActivity
import com.google.android.gms.location.places.ui.PlacePicker.getPlace
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.text.SimpleDateFormat
import java.util.*


class CreateGroupRideActivity : BaseActivity(), CreateGroupRideView, OnMapReadyCallback {

    companion object {
        val TAG = CreateGroupRideActivity::class.java.simpleName
        const val REQUEST_START_PLACE_PICKER = 1
        const val REQUEST_FINISH_PLACE_PICKER = 2
        fun getIntent(context: Context) = Intent(context, CreateGroupRideActivity::class.java)
    }

    @InjectPresenter
    lateinit var presenter: CreateGroupRidePresenter
    private lateinit var map: GoogleMap
    private var markerStart: Marker? = null
    private var markerFinish: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group_ride)

        choose_start.setOnClickListener { presenter.startPointClicked() }
        choose_finish.setOnClickListener { presenter.finishPointClicked() }
        date_time.setOnClickListener { presenter.chooseDateAndTime() }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_group_ride_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.create_group_ride) {
            presenter.createGroupRide(title_et.text.toString(), description_et.text.toString())
        }
        return true
    }

    override fun chooseDate() {
        val calendar = Calendar.getInstance()
        val year = Calendar.YEAR
        val month = Calendar.MONTH
        val day = Calendar.DAY_OF_MONTH
        val dateDialog = DatePickerDialog(this,
                { _, years: Int, months: Int, days: Int ->
                    calendar.set(year, years)
                    calendar.set(month, months)
                    calendar.set(day, days)
                    presenter.dateChoosed(calendar)
                },
                calendar.get(year),
                calendar.get(month),
                calendar.get(day)
        )
        dateDialog.show()
    }

    override fun chooseTime(calendar: Calendar) {
        val hour = Calendar.HOUR
        val minute = Calendar.MINUTE
        val timeDialog = TimePickerDialog(this,
                { _, hours: Int, minutes: Int ->
                    calendar.set(hour, hours)
                    calendar.set(minute, minutes)
                    presenter.timeChoosed(calendar)
                },
                calendar.get(hour),
                calendar.get(minute),
                true)
        timeDialog.show()
    }

    override fun dateTimeChoosed(calendar: Calendar) {
        date_time.text = SimpleDateFormat("d MMMM yyyy HH:mm", Locale.getDefault()).format(calendar.time)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ((requestCode == REQUEST_START_PLACE_PICKER || requestCode == REQUEST_FINISH_PLACE_PICKER)
                && resultCode == Activity.RESULT_OK) {
            val place = getPlace(this, data)

            if (requestCode == REQUEST_START_PLACE_PICKER)
                presenter.startPointChoosed(place)
            else
                presenter.finishPointChoosed(place)
        } else {
            presenter.nothingChoosed()
        }
    }

    override fun showMap() {
    }

    override fun addMarker(coordinates: LatLng, pointName: String, isStart: Boolean) {
        if (isStart) {
            markerStart?.remove()
            markerStart = map.addMarker(MarkerOptions().position(coordinates)
                    .title("Start")
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
            choose_start.setText(pointName)
            choose_start_layout.hint = "Start Point"
        } else {
            markerFinish?.remove()
            markerFinish = map.addMarker(MarkerOptions().position(coordinates)
                    .title("Finish")
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED)))
            choose_finish.setText(pointName)
            choose_finish_layout.hint = "Finish Point"
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(coordinates))
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

    override fun closeScreen() {
        finish()
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
