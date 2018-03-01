package com.diploma.volodymyr.bicyclecity.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl.GroupRidesListPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.groupride.GroupRidesListView
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseFragment

class GroupRidesFragment : BaseFragment(), GroupRidesListView {

    @InjectPresenter
    lateinit var presenter: GroupRidesListPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_group_rides, container, false)
    }

}