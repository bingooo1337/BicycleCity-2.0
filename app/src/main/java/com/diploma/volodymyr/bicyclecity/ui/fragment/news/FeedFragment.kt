package com.diploma.volodymyr.bicyclecity.ui.fragment.news

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.data.objects.FeedObject
import com.diploma.volodymyr.bicyclecity.presentation.presenter.news.impl.FeedPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.news.FeedView
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseFragment
import com.diploma.volodymyr.bicyclecity.ui.adapters.news.RecyclerFeedAdapter
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : BaseFragment(), FeedView {

    companion object {
        private const val IS_NEWS_FRAGMENT = "is_news_fragment"

        fun getInstance(isNews: Boolean) = FeedFragment()
                .apply { arguments = Bundle().apply { putBoolean(IS_NEWS_FRAGMENT, isNews) } }
    }

    @InjectPresenter
    lateinit var presenter: FeedPresenter
    private lateinit var adapter: RecyclerFeedAdapter
    private var isNews: Boolean = true

    @ProvidePresenter
    fun providePresenter() = FeedPresenter(isNews)

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.let { isNews = it.getBoolean(IS_NEWS_FRAGMENT) }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun showLoading() {
        feed_refresh_layout.isRefreshing = true
    }

    override fun hideLoading() {
        feed_refresh_layout.isRefreshing = false
    }

    override fun showFeedObjects(feedList: List<FeedObject>) {
        adapter.feedList = feedList
    }

    private fun initView() {
        adapter = RecyclerFeedAdapter { }

        feed_recycler.layoutManager = LinearLayoutManager(context)
        feed_recycler.adapter = adapter
    }
}