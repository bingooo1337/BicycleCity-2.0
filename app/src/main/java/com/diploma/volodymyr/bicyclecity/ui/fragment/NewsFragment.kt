package com.diploma.volodymyr.bicyclecity.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseFragment
import com.diploma.volodymyr.bicyclecity.ui.adapters.news.NewsPagerAdapter
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : BaseFragment() {

    private lateinit var pagerAdapter : NewsPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = NewsPagerAdapter(childFragmentManager)

        news_viewpager.adapter = pagerAdapter
        news_tab_layout.setupWithViewPager(news_viewpager)
    }
}