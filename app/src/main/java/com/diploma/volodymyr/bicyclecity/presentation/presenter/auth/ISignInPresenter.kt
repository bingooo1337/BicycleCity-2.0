package com.diploma.volodymyr.bicyclecity.presentation.presenter.auth

interface ISignInPresenter {
    fun signIn(login: String, password: String)
    fun signUpClicked()
    fun registrationClicked()
}