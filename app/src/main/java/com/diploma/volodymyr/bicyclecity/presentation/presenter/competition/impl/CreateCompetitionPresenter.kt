package com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.impl

import android.content.ClipData
import android.content.ClipboardManager
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.App
import com.diploma.volodymyr.bicyclecity.common.getFormattedDateString
import com.diploma.volodymyr.bicyclecity.common.getTimeString
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.ICreateCompetitionPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.competition.CreateCompetitionView
import java.util.*

@InjectViewState
class CreateCompetitionPresenter : BasePresenter<CreateCompetitionView>(), ICreateCompetitionPresenter {

    private var code = "A1B2C3D4"

    override fun onIsPrivateChanged(isPrivate: Boolean) {
        if (isPrivate) viewState.showPrivateCode(code)
        else viewState.hidePrivateCode()
    }

    override fun onCopyCodeClicked(clipboardManager: ClipboardManager?) {
        clipboardManager?.let {
            it.primaryClip = ClipData.newPlainText(code, code)
            viewState.showToastMessage(App.instance.getString(R.string.value_was_copied, code))
        }
    }

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

    override fun onCreateClicked() {

    }
}