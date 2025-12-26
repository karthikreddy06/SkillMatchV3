package com.simats.SkillMatchV3.ui.theme.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simats.SkillMatchV3.network.ApiClient
import com.simats.SkillMatchV3.network.ApiService
import com.simats.SkillMatchV3.network.UpdateProfileRequest
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    var isLoading by mutableStateOf(false)
    var message by mutableStateOf<String?>(null)

    fun updateProfile(
        token: String,
        name: String,
        phone: String
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                // Fix: Create the ApiService instance from Retrofit
                val apiService = ApiClient.retrofit.create(ApiService::class.java)

                val response = apiService.updateProfile(
                    token = "Bearer $token", // Ensure "Bearer " is included if not added elsewhere
                    request = UpdateProfileRequest(
                        name = name,
                        phone = phone,
                        latitude = null,
                        longitude = null
                    )
                )
                message = response.message
            } catch (e: Exception) {
                e.printStackTrace()
                message = "Update failed: ${e.localizedMessage}"
            }
            isLoading = false
        }
    }
}
