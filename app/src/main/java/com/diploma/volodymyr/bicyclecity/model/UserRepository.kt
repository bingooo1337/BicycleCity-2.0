package com.diploma.volodymyr.bicyclecity.model

interface UserRepository {
    fun createUser(login: String, password: String)
    fun loginUser(login: String, password: String)
}