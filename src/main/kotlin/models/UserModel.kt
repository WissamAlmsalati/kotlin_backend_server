package org.example.models


data class User(val id: Int, val email: String, val username: String, val password: String)

data class LoginRequest(val email: String , val password: String)

data class UpdateRequest(val username: String? = null, val password: String? = null)


