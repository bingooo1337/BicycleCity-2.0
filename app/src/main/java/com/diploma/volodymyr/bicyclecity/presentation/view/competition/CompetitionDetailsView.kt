package com.diploma.volodymyr.bicyclecity.presentation.view.competition

import com.diploma.volodymyr.bicyclecity.data.objects.User
import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView
import com.google.android.gms.maps.model.MarkerOptions

interface CompetitionDetailsView : BaseView {
    fun showLoading()
    fun hideLoading()
    fun enableJoinButton()
    fun disableJoinButton()
    fun showDoneButton()
    fun showReadyButton(text: String)
    fun showReadyCount(readyCount: Int, usersCount: Int)
    fun showCompetitionDetails(competition: Competition)
    fun showCreatorInfo(user: User)
    fun showMarkers(start: MarkerOptions, finish: MarkerOptions)
    fun showUsersList(userNames: String)
    fun startTrackingLocation(competitionId: String)
}