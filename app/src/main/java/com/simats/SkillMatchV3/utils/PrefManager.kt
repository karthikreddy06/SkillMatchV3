package com.simats.SkillMatchV3.utils

import android.content.Context

class PrefManager(context: Context) {

    private val prefs =
        context.getSharedPreferences("skillmatch_prefs", Context.MODE_PRIVATE)

    // ---------------- ONBOARDING ----------------

    fun setOnboardingSeen() {
        prefs.edit()
            .putBoolean("onboarding_seen", true)
            .apply()
    }

    fun hasSeenOnboarding(): Boolean =
        prefs.getBoolean("onboarding_seen", false)

    // ---------------- LOGIN ----------------

    fun setLoggedIn(value: Boolean) {
        prefs.edit()
            .putBoolean("logged_in", value)
            .apply()
    }

    fun isLoggedIn(): Boolean =
        prefs.getBoolean("logged_in", false)

    // ---------------- TOKEN ----------------

    fun saveToken(token: String) {
        prefs.edit()
            .putString("token", token)
            .apply()
    }

    fun getToken(): String? =
        prefs.getString("token", null)

    // ---------------- ROLE ----------------

    fun setRole(role: String) {
        prefs.edit()
            .putString("role", role)
            .apply()
    }

    fun getRole(): String? =
        prefs.getString("role", null)

    // ---------------- USER NAME ----------------

    fun setUserName(name: String) {
        prefs.edit()
            .putString("user_name", name)
            .apply()
    }

    fun getUserName(): String? =
        prefs.getString("user_name", null)

    // ---------------- SKILLS ----------------

    fun saveSkills(skills: List<String>) {
        prefs.edit()
            .putStringSet("skills", skills.toSet())
            .apply()
    }

    fun getSkills(): List<String> =
        prefs.getStringSet("skills", emptySet())?.toList() ?: emptyList()

    // ---------------- LOGOUT ----------------

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
