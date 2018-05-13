package com.diploma.volodymyr.bicyclecity.presentation.view.groupride

import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.data.objects.User
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions

interface GroupRideDetailsView : BaseView {
    fun showLoading()
    fun hideLoading()
    fun enableJoinButton()
    fun disableJoinButton()
    fun showDoneButton()
    fun showGroupRideDetails(groupRide: GroupRide)
    fun showCreatorInfo(user: User)
    fun showRoute(start: LatLng, finish: LatLng, polyline: PolylineOptions)
    fun showUsersList(userNames: String)
}