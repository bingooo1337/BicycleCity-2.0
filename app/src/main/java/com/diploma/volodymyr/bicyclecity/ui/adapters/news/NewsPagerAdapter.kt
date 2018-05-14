package com.diploma.volodymyr.bicyclecity.ui.adapters.news

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.App
import com.diploma.volodymyr.bicyclecity.ui.fragment.news.FeedFragment

class NewsPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {

    companion object {
        private const val FRAGMENTS_COUNT = 2
        private const val NEWS_FRAGMENT = 0
        private const val EVENTS_FRAGMENT = 1
    }

    override fun getItem(position: Int): Fragment =
            FeedFragment.getInstance(position == NEWS_FRAGMENT)

    override fun getCount(): Int = FRAGMENTS_COUNT

    override fun getPageTitle(position: Int): CharSequence =
            if (position == NEWS_FRAGMENT) App.instance.getString(R.string.news)
            else App.instance.getString(R.string.events)
}