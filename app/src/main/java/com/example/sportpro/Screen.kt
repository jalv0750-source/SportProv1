package com.example.sportpro

sealed class Screen {
    data object Login : Screen()
    data object RoleSelection : Screen()
    data class RegisterForm(val role: String) : Screen()
}
