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
import com.diploma.volodymyr.bicyclecity.ui.adapters.RecyclerGroupRidesAdapter
import kotlinx.android.synthetic.main.fragment_group_rides.*

class GroupRidesFragment : BaseFragment(), GroupRidesListView {

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
        callData()
    }

    override fun showData(rides: List<GroupRide>) {
        adapter.rides = rides
        swipe_layout.isRefreshing = false
    }

    private fun initView() {
        swipe_layout.setOnRefreshListener { callData() }
        adapter = RecyclerGroupRidesAdapter { showToastMessage("Group Ride") }
        group_rides_recycler.adapter = adapter
        group_rides_recycler.layoutManager = LinearLayoutManager(context)
    }

    private fun callData() {
        swipe_layout.isRefreshing = true
        presenter.loadData()
    }
}