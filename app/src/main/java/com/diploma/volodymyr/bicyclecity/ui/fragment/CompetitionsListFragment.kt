package com.diploma.volodymyr.bicyclecity.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.impl.CompetitionsListPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.competition.CompetitionsListView
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseFragment
import com.diploma.volodymyr.bicyclecity.ui.adapters.RecyclerCompetitionsAdapter
import kotlinx.android.synthetic.main.fragment_competitions.*

class CompetitionsListFragment : BaseFragment(), CompetitionsListView {

    @InjectPresenter
    lateinit var presenter: CompetitionsListPresenter
    private lateinit var adapter: RecyclerCompetitionsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_competitions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun showLoading() {
        swipe_layout.isRefreshing = true
    }

    override fun hideLoading() {
        swipe_layout.isRefreshing = false
    }

    override fun showCompetitions(competitions: List<Competition>) {
        adapter.competitions = competitions
    }

    private fun initView() {
        adapter = RecyclerCompetitionsAdapter { showToastMessage(it.title) }

        competitions_recycler.layoutManager = LinearLayoutManager(context!!)
        competitions_recycler.adapter = adapter
    }
}