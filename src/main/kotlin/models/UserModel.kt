package org.example.models



data class UserModel(
    val id: Int, // changed from String to Int
    val username: String,
    val email: String,
    val password: String,
    val phone: String
)


data class LoginRequest(
    val email: String,
    val password: String)
data class SignUpRequest(
    val id: Int,
    val email: String,
    val password: String,
    val phone: String,
    val username: String,
)

data class UpdateRequest(
    val username: String,
    val password: String,
)


