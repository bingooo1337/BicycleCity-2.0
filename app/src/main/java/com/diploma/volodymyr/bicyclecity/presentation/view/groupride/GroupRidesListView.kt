package com.diploma.volodymyr.bicyclecity.presentation.view.groupride

import com.diploma.volodymyr.bicyclecity.data.model.GroupRide
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView

interface GroupRidesListView: BaseView {
    fun showData(rides: List<GroupRide>)
}