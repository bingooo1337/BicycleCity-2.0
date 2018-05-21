package com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.impl

import android.content.ClipData
import android.content.ClipboardManager
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.App
import com.diploma.volodymyr.bicyclecity.common.getFormattedDateString
import com.diploma.volodymyr.bicyclecity.common.getTimeString
import com.diploma.volodymyr.bicyclecity.data.objects.competition.BicycleType
import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.data.objects.competition.TrainingLevel
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.ICreateCompetitionPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.competition.CreateCompetitionView
import java.util.*

@InjectViewState
class CreateCompetitionPresenter : BasePresenter<CreateCompetitionView>(), ICreateCompetitionPresenter {

    private var isPrivate: Boolean = false
    private var hasPrize: Boolean = false
    private var code = "A1B2C3D4"

    override fun onIsPrivateChanged(isPrivate: Boolean) {
        this.isPrivate = isPrivate

        if (isPrivate) viewState.showPrivateCode(code)
        else viewState.hidePrivateCode()
    }

    override fun addPrizeClicked(hasPrize: Boolean) {
        this.hasPrize = hasPrize

        if (hasPrize) viewState.showPrizeEditText()
        else viewState.hidePrizeEditText()
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

    override fun onCreateClicked(title: String, desc: String, bicycleTypePosition: Int, levelPosition: Int, prize: String) {
        App.instance.getString(R.string.required).let {
            val titleIsEmpty = title.isBlank()
            val descIsEmpty = desc.isBlank()
            viewState.setErrorHints(
                    if (titleIsEmpty) it else "",
                    if (descIsEmpty) it else "")
            if (titleIsEmpty || descIsEmpty) return
        }

        val bicycleType = when (bicycleTypePosition) {
            1 -> BicycleType.MOUNTAIN
            2 -> BicycleType.TRACK
            3 -> BicycleType.TIME_TRIAL
            else -> BicycleType.ROAD
        }
        val trainingLevel = when (levelPosition) {
            1 -> TrainingLevel.ADVANCED_BEGINNER
            2 -> TrainingLevel.AMATEUR
            3 -> TrainingLevel.EVERYDAY_RIDER
            4 -> TrainingLevel.PROFESSIONAL
            else -> TrainingLevel.BEGINNER
        }
        val competitionPrize = if (hasPrize && prize.isNotBlank()) prize else null
        val competitionCode = if (isPrivate && code.isNotBlank()) code else null

        val competition = Competition(title = title, description = desc, isPrivate = isPrivate,
                accessCode = competitionCode, prize = competitionPrize, date = calendar.time,
                bicycleType = bicycleType, trainingLevel = trainingLevel)
        viewState.goToNextScreen(competition)
    }
}