package com.diploma.volodymyr.bicyclecity.presentation.presenter.auth

interface ISignUpPresenter {
    fun signUp(firstName: String,
               lastName: String,
               email: String,
               number: String,
               password: String)
}