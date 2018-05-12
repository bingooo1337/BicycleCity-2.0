package com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl

import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.common.App
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.common.getFormattedDateString
import com.diploma.volodymyr.bicyclecity.common.getTimeString
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.ICreateGroupRidePresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.groupride.CreateGroupRideView
import java.util.*

@InjectViewState
class CreateGroupRidePresenter : BasePresenter<CreateGroupRideView>(), ICreateGroupRidePresenter {

    private var calendar = Calendar.getInstance()

    override fun onDateCLicked() {
        viewState.showDatePicker()
    }

    override fun onTimeCLicked() {
        viewState.showTimePicker()
    }

    override fun onSelectClicked(day: Int, month: Int, year: Int) {
        viewState.hidePickers()
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)
        viewState.setDate(calendar.time.getFormattedDateString())
    }

    override fun onSelectClicked(hour: Int, minute: Int) {
        viewState.hidePickers()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        viewState.setTime(calendar.time.getTimeString())
    }

    override fun onContinueClicked(title: String, desc: String, avgSpeedItemPosition: Int) {
        App.instance.getString(R.string.required).let {
            val titleIsEmpty = title.isBlank()
            val descIsEmpty = desc.isBlank()
            viewState.setErrorHints(if (titleIsEmpty) it else "", if (descIsEmpty) it else "")
            if (titleIsEmpty || descIsEmpty) return
        }

        val avgSpeed = App.instance.resources
                .getStringArray(R.array.avg_speed)[avgSpeedItemPosition]
                .substring(0, 2)
                .toInt()
        val groupRide = GroupRide(title, desc, calendar.time, avgSpeed = avgSpeed)
        viewState.goToNextScreen(groupRide)
    }
}
