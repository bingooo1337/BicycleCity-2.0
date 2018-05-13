package com.diploma.volodymyr.bicyclecity.presentation.presenter.auth.impl

import com.arellomobile.mvp.InjectViewState
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

        if (firstName.isEmpty()) {
            viewState.showFirstNameError("Required")
            validated = false
        }
        if (lastName.isEmpty()) {
            viewState.showLastNameError("Required")
            validated = false
        }
        if (email.isEmpty()) {
            viewState.showEmailError("Required")
            validated = false
        }
        if (number.isEmpty()) {
            viewState.showNumberError("Required")
            validated = false
        }
        if (password.isEmpty()) {
            viewState.showPasswordError("Required")
            validated = false
        }

        return validated
    }
}