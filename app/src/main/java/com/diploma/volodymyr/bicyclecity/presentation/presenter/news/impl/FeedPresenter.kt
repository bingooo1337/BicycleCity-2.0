package com.diploma.volodymyr.bicyclecity.presentation.presenter.news.impl

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.data.objects.FeedObject
import com.diploma.volodymyr.bicyclecity.model.FeedRepository
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.news.IFeedPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.news.FeedView
import javax.inject.Inject

@InjectViewState
class FeedPresenter(private val isNews: Boolean) : BasePresenter<FeedView>(), IFeedPresenter {

    init {
        dataComponent.inject(this)
    }

    @Inject
    lateinit var repository: FeedRepository

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadFeed(isNews)
    }

    private fun loadFeed(isNews: Boolean) {
        viewState.showLoading()

        if (isNews) {
            repository.getNews().addSnapshotListener { snapshot, ex ->
                viewState.hideLoading()

                if (ex != null) {
                    ex.printStackTrace()
                    return@addSnapshotListener
                }

                snapshot?.let {
                    viewState.showFeedObjects(it.toObjects(FeedObject::class.java))
                }
            }
        } else {

        }
    }
}