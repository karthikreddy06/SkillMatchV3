package com.simats.SkillMatchV3.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simats.SkillMatchV3.network.ApiClient
import com.simats.SkillMatchV3.network.ApiService
import com.simats.SkillMatchV3.network.GenericResponse
import com.simats.SkillMatchV3.network.UpdateProfileRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    fun updateProfile(
        token: String,
        request: UpdateProfileRequest
    ) {
        _loading.value = true
        _error.value = null
        _success.value = false

        val api = ApiClient.retrofit.create(ApiService::class.java)

        api.updateProfile("Bearer $token", request)
            .enqueue(object : Callback<GenericResponse> {

                override fun onResponse(
                    call: Call<GenericResponse>,
                    response: Response<GenericResponse>
                ) {
                    _loading.value = false

                    if (response.isSuccessful && response.body()?.status == true) {
                        _success.value = true
                    } else {
                        _error.value =
                            response.body()?.message ?: "Profile update failed"
                    }
                }

                override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                    _loading.value = false
                    _error.value = t.localizedMessage ?: "Network error"
                }
            })
    }
}
