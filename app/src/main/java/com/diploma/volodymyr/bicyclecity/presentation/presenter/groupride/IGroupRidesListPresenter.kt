package com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride

import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide

interface IGroupRidesListPresenter {
    fun loadData()
    fun onGroupRideClicked(groupRide: GroupRide)
}