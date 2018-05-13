package com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl

import android.graphics.Color
import android.util.Log
import com.akexorcist.googledirection.util.DirectionConverter
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.*
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.data.objects.User
import com.diploma.volodymyr.bicyclecity.model.GroupRideRepository
import com.diploma.volodymyr.bicyclecity.model.UserRepository
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.IGroupRideDetailsPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.groupride.GroupRideDetailsView
import com.google.maps.android.PolyUtil
import javax.inject.Inject

@InjectViewState
class GroupRideDetailsPresenter(private val groupRideId: String) :
        BasePresenter<GroupRideDetailsView>(), IGroupRideDetailsPresenter {

    companion object {
        private val TAG = GroupRideDetailsPresenter::class.java.simpleName
    }

    init {
        dataComponent.inject(this)
    }

    @Inject
    lateinit var groupRideRepository: GroupRideRepository
    @Inject
    lateinit var userRepository: UserRepository

    private lateinit var groupRide: GroupRide

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadGroupRide(groupRideId)
    }

    override fun onJoinClicked() {
        viewState.disableJoinButton()
        viewState.showLoading()

        userRepository.getCurrentUser()?.let {
            groupRide.users?.add(it.uid)
            groupRideRepository.updateGroupRide(groupRideId, groupRide)
                    .subscribe({
                        viewState.hideLoading()
                        viewState.showDoneButton()
                    }, {
                        viewState.hideLoading()
                        viewState.enableJoinButton()
                        viewState.showToastMessage(R.string.loading_failed)
                        Log.e(TAG, it.localizedMessage)
                        it.printStackTrace()
                    })
        }
    }

    private fun loadGroupRide(groupRideId: String) {
        viewState.showLoading()

        groupRideRepository.getGroupRideById(groupRideId)
                .addSnapshotListener { snapshot, exception ->
                    viewState.hideLoading()

                    if (exception != null) {
                        viewState.showToastMessage(R.string.loading_failed)
                        Log.e(TAG, exception.localizedMessage)
                        exception.printStackTrace()
                        return@addSnapshotListener
                    }

                    snapshot?.toObject(GroupRide::class.java)?.let {
                        groupRide = it

                        if (it.users?.contains(userRepository.getCurrentUser()?.uid) == true)
                            viewState.showDoneButton()
                        else
                            viewState.enableJoinButton()

                        viewState.showGroupRideDetails(it)
                        it.users?.let { users -> loadUsersList(users, it.creatorId) }

                        it.start?.let { start ->
                            it.finish?.let { finish ->
                                viewState.showRoute(start.getLatLng(), finish.getLatLng(),
                                        PolyUtil.decode(it.encodedRoute).getPolyLineOptions())
                            }
                        }
                    }
                }
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
                                    .map { App.instance.getString(R.string.user_full_name_placeholder, it.firstName, it.lastName) }
                                    .reduce { acc, s -> acc + "\n" + s })
                    }
        }
    }
}