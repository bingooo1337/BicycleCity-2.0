package com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride

interface ICreateGroupRidePresenter {
    fun onDateCLicked()
    fun onTimeCLicked()
    fun onSelectClicked(day: Int, month: Int, year: Int)
    fun onSelectClicked(hour: Int, minute: Int)
    fun onContinueClicked(title: String, desc: String, avgSpeedItemPosition: Int)
}