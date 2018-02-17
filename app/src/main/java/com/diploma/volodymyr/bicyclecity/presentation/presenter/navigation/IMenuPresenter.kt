package com.diploma.volodymyr.bicyclecity.presentation.presenter.navigation

import android.view.MenuItem

interface IMenuPresenter {
    fun menuItemClicked(item: MenuItem)
    fun optionsItemClicked(id: Int)
}