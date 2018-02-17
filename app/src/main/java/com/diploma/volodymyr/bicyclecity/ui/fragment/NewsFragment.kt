package com.diploma.volodymyr.bicyclecity.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseFragment

class NewsFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }
}