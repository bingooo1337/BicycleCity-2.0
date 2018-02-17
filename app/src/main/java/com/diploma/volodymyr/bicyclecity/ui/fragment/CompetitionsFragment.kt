package com.diploma.volodymyr.bicyclecity.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseFragment


/**
 * A simple [Fragment] subclass.
 */
class CompetitionsFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_competitions, container, false)
    }

}// Required empty public constructor
