package com.diploma.volodymyr.bicyclecity.presentation.view.news

import com.diploma.volodymyr.bicyclecity.data.objects.FeedObject
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView

interface FeedView : BaseView {
    fun showLoading()
    fun hideLoading()
    fun showFeedObjects(feedList: List<FeedObject>)
}