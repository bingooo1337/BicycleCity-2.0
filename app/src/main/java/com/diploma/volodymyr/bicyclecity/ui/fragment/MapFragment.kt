package com.diploma.volodymyr.bicyclecity.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.data.objects.Marker
import com.diploma.volodymyr.bicyclecity.getLatLng
import com.diploma.volodymyr.bicyclecity.presentation.presenter.map.impl.MapPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.map.MapView
import com.diploma.volodymyr.bicyclecity.setInvisible
import com.diploma.volodymyr.bicyclecity.setVisible
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : BaseFragment(), MapView, OnMapReadyCallback {

    companion object {
        private const val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    @InjectPresenter
    lateinit var presenter: MapPresenter
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map.onCreate(savedInstanceState?.getBundle(MAP_VIEW_BUNDLE_KEY))
        map.getMapAsync(this)
        presenter.loadMarkers()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        (outState.getBundle(MAP_VIEW_BUNDLE_KEY) ?: Bundle()).let {
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, it)
            map.onSaveInstanceState(it)
        }
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onStart() {
        super.onStart()
        map.onStart()
    }

    override fun onStop() {
        super.onStop()
        map.onStop()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map.onLowMemory()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
    }

    override fun showLoading() {
        progress_bar.setVisible()
    }

    override fun hideLoading() {
        progress_bar.setInvisible()
    }

    override fun showMarkers(markers: List<Marker>) {
        markers.forEach {
            googleMap.addMarker(MarkerOptions().apply {
                position(it.geo.getLatLng())
                title(it.name)
            })
        }
    }
}
