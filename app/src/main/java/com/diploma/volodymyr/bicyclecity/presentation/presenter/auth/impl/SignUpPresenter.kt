package com.diploma.volodymyr.bicyclecity.presentation.presenter.auth.impl

import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.App
import com.diploma.volodymyr.bicyclecity.model.UserRepository
import com.diploma.volodymyr.bicyclecity.presentation.presenter.auth.ISignUpPresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.auth.SignUpView
import javax.inject.Inject

@InjectViewState
class SignUpPresenter : BasePresenter<SignUpView>(), ISignUpPresenter {

    @Inject
    lateinit var userRepository: UserRepository

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        dataComponent.inject(this)
    }

    override fun signUp(firstName: String, lastName: String, email: String, number: String, password: String) {
        if (validate(firstName, lastName, email, number, password)) {
            viewState.showLoading()

            userRepository.createUser(firstName, lastName, email, number, password) { isSuccess, error ->
                viewState.hideLoading()
                if (isSuccess) {
                    viewState.showToastMessage("Successful")
                    viewState.closeScreen()
                } else {
                    viewState.showToastMessage(error ?: "Registration failure")
                }
            }
        }
    }

    private fun validate(firstName: String, lastName: String, email: String, number: String, password: String): Boolean {
        var validated = true
        val error = App.INSTANSE.getString(R.string.required)

        if (firstName.isEmpty()) {
            viewState.showFirstNameError(error)
            validated = false
        }
        if (lastName.isEmpty()) {
            viewState.showLastNameError(error)
            validated = false
        }
        if (email.isEmpty()) {
            viewState.showEmailError(error)
            validated = false
        }
        if (number.isEmpty()) {
            viewState.showNumberError(error)
            validated = false
        }
        if (password.isEmpty()) {
            viewState.showPasswordError(error)
            validated = false
        }

        return validated
    }
}