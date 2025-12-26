package com.simats.SkillMatchV3.network

/* =========================
   LOGIN
   ========================= */

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val status: Boolean,
    val message: String?,
    val access_token: String?,
    val user: User?
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val is_verified: Boolean,
    val company_id: Int?
)

/* =========================
   REGISTER
   ========================= */

data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: String = "com/simats/SkillMatchV3/ui/theme/screens/seeker"
)

data class RegisterResponse(
    val status: Boolean,
    val message: String?
)
