package com.diploma.volodymyr.bicyclecity.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl.GroupRidesListPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.groupride.GroupRidesListView
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseFragment
import com.diploma.volodymyr.bicyclecity.ui.activity.groupride.GroupRideDetailsActivity
import com.diploma.volodymyr.bicyclecity.ui.adapters.RecyclerGroupRidesAdapter
import kotlinx.android.synthetic.main.fragment_group_rides.*

class GroupRidesListFragment : BaseFragment(), GroupRidesListView {

    @InjectPresenter
    lateinit var presenter: GroupRidesListPresenter
    private lateinit var adapter: RecyclerGroupRidesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_group_rides, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadData()
    }

    override fun showData(rides: List<GroupRide>) {
        adapter.rides = rides
        swipe_layout.isRefreshing = false
    }

    override fun showLoading() {
        swipe_layout.isRefreshing = true
    }

    override fun hideLoading() {
        swipe_layout.isRefreshing = false
    }

    override fun openGroupRide(groupRideTitle: String, groupRideId: String) {
        startActivity(GroupRideDetailsActivity.getIntent(context!!, groupRideTitle, groupRideId))
    }

    private fun initView() {
        swipe_layout.setOnRefreshListener { presenter.loadData() }
        adapter = RecyclerGroupRidesAdapter { presenter.onGroupRideClicked(it) }
        group_rides_recycler.adapter = adapter
        group_rides_recycler.layoutManager = LinearLayoutManager(context)
    }
}