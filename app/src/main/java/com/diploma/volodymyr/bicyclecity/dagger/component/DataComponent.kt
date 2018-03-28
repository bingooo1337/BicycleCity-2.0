package com.diploma.volodymyr.bicyclecity.dagger.component

import com.diploma.volodymyr.bicyclecity.dagger.module.RepositoryModule
import com.diploma.volodymyr.bicyclecity.presentation.presenter.auth.impl.SignInPresenter
import dagger.Component

@Component(modules = [RepositoryModule::class])
interface DataComponent {

    fun inject(presenter: SignInPresenter)
}