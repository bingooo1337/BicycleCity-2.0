package com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.impl

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.App
import com.diploma.volodymyr.bicyclecity.common.competition.LocationTrackService
import com.diploma.volodymyr.bicyclecity.common.competition.ResultCalculator
import com.diploma.volodymyr.bicyclecity.common.getLatLng
import com.diploma.volodymyr.bicyclecity.common.getPolyLineOptions
import com.diploma.volodymyr.bicyclecity.common.toMarkerOptions
import com.diploma.volodymyr.bicyclecity.data.objects.User
import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.model.CompetitionRepository
import com.diploma.volodymyr.bicyclecity.model.UserRepository
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.ICompetitionDetailsPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.competition.CompetitionDetailsView
import com.google.maps.android.PolyUtil
import javax.inject.Inject

@InjectViewState
class CompetitionDetailsPresenter(private val competitionId: String) :
        BasePresenter<CompetitionDetailsView>(), ICompetitionDetailsPresenter {

    companion object {
        private val TAG = CompetitionDetailsPresenter::class.java.simpleName
    }

    init {
        dataComponent.inject(this)
    }

    @Inject
    lateinit var repository: CompetitionRepository
    @Inject
    lateinit var userRepository: UserRepository

    private lateinit var competition: Competition
    private val trackService = LocationTrackService(competitionId)
    private val resultCalculator = ResultCalculator(mapOf())

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        repository.getCompetitionById(competitionId)
                .addSnapshotListener { snapshot, ex ->
                    if (ex != null) {
                        viewState.showToastMessage(R.string.loading_failed)
                        Log.e(TAG, ex.localizedMessage)
                        ex.printStackTrace()
                        return@addSnapshotListener
                    }

                    snapshot?.toObject(Competition::class.java)?.let {
                        competition = it

                        if (it.users.contains(userRepository.getCurrentUser()?.uid))
                            viewState.showDoneButton()
                        else
                            viewState.enableJoinButton()

                        viewState.showCompetitionDetails(it)
                        it.users.let { users -> loadUsersList(users, it.creatorId) }

                        val start = it.start?.getLatLng().toMarkerOptions(true)
                        val finish = it.finish?.getLatLng().toMarkerOptions(false)
                        if (start != null && finish != null) viewState.showMarkers(start, finish)

                        if (it.users.contains(userRepository.getCurrentUser()?.uid)) {
                            val readyCount = it.readyUsers?.size ?: 0
                            viewState.showReadyButton(App.INSTANSE.getString(
                                    if (it.users.size - readyCount == 1) R.string.start
                                    else R.string.ready))
                            viewState.showReadyCount(readyCount, it.users.size)
                        }
                    }
                }
    }

    override fun onJoinClicked() {

    }

    override fun onReadyClicked() {

    }

    private fun loadUsersList(usersIds: List<String>, creatorId: String) {
        val users = arrayListOf<User>()

        usersIds.forEach { id ->
            userRepository.getUserById(id)
                    .addOnSuccessListener {
                        it.toObject(User::class.java)?.let {
                            users.add(it)

                            if (id == creatorId) viewState.showCreatorInfo(it)
                        }
                        if (users.size == usersIds.size)
                            viewState.showUsersList(users
                                    .map {
                                        App.INSTANSE.getString(R.string.user_full_name_placeholder,
                                                it.firstName, it.lastName)
                                    }
                                    .reduce { acc, s -> acc + "\n" + s })
                    }
        }
    }
}