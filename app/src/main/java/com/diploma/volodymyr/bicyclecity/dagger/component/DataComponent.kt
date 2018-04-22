package com.diploma.volodymyr.bicyclecity.dagger.component

import com.diploma.volodymyr.bicyclecity.dagger.module.RepositoryModule
import com.diploma.volodymyr.bicyclecity.presentation.presenter.auth.impl.SignInPresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.auth.impl.SignUpPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface DataComponent {

    fun inject(presenter: SignInPresenter)
    fun inject(presenter: SignUpPresenter)
}