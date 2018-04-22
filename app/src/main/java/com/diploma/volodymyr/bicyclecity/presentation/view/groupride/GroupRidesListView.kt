package com.diploma.volodymyr.bicyclecity.presentation.view.groupride

import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView

interface GroupRidesListView: BaseView {
    fun showData(rides: List<GroupRide>)
}