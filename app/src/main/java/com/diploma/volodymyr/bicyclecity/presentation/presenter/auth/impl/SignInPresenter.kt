package com.diploma.volodymyr.bicyclecity.presentation.presenter.auth.impl

import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.common.App
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.model.UserRepository
import com.diploma.volodymyr.bicyclecity.presentation.presenter.auth.ISignInPresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.auth.SignInView
import javax.inject.Inject

@InjectViewState
class SignInPresenter : BasePresenter<SignInView>(), ISignInPresenter {

    @Inject
    lateinit var userRepository: UserRepository

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        dataComponent.inject(this)
    }

    override fun checkCurrentUser() {
        userRepository.getCurrentUser()?.let {
            viewState.openApp()
        }
    }

    override fun signIn(login: String, password: String) {
        if (validate(login, password)) {
            viewState.showLoading()
            userRepository.loginUser(login, password)
                    .addOnSuccessListener {
                        viewState.hideLoading()
                        viewState.openApp()
                    }
                    .addOnFailureListener {
                        viewState.hideLoading()
                        if (it.localizedMessage != null)
                            viewState.showToastMessage(it.localizedMessage)
                        else
                            viewState.showToastMessage("Sign in failure")
                    }
        }
    }

    override fun signUpClicked() {
        viewState.startSignUp()
    }

    private fun validate(login: String, password: String): Boolean {
        var validated = true

        if (login.isEmpty()) {
            viewState.showLoginError(App.instance.getString(R.string.required))
            validated = false
        }
        if (password.isEmpty()) {
            viewState.showPasswordError(App.instance.getString(R.string.required))
            validated = false
        }

        return validated
    }
}
