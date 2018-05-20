package com.diploma.volodymyr.bicyclecity.presentation.presenter.competition

import android.content.ClipboardManager

interface ICreateCompetitionPresenter {
    fun onIsPrivateChanged(isPrivate: Boolean)
    fun onCopyCodeClicked(clipboardManager: ClipboardManager?)
    fun onDateCLicked()
    fun onTimeCLicked()
    fun onSelectClicked(day: Int, month: Int, year: Int)
    fun onSelectClicked(hour: Int, minute: Int)
    fun onCreateClicked()
}