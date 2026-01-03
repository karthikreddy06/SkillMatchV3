package com.simats.SkillMatchV3.ui.screens.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.simats.SkillMatchV3.navigation.NavRoutes
import com.simats.SkillMatchV3.network.ApiClient
import com.simats.SkillMatchV3.network.ApiService
import com.simats.SkillMatchV3.network.LoginRequest
import com.simats.SkillMatchV3.network.LoginResponse
import com.simats.SkillMatchV3.utils.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

@Composable
fun LoginScreen(navController: NavHostController) {

    val context = LocalContext.current
    val prefManager = remember { PrefManager(context) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Login",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            onClick = {

                if (email.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Enter email & password", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                isLoading = true
                Log.e("LOGIN_TEST", "Sending login request")

                val api = ApiClient.retrofit.create(ApiService::class.java)

                api.login(LoginRequest(email, password))
                    .enqueue(object : Callback<LoginResponse> {

                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            isLoading = false // Stop loading immediately on response
                            val body = response.body()

                            if (response.isSuccessful && body?.status == true) {

                                Log.e("LOGIN_TEST", "Login success")

                                val token = body.access_token
                                val rawRole = body.user?.role
                                val name = body.user?.name

                                if (token.isNullOrBlank() || rawRole.isNullOrBlank()) {
                                    Toast.makeText(
                                        context,
                                        "Invalid login response",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return
                                }

                                // Normalize role from API (backend source of truth)
                                val apiRole = rawRole.trim().lowercase(Locale.ROOT)

                                // Get the role selected by user in the previous screen
                                val chosenRole = prefManager.getRole()?.lowercase(Locale.ROOT)

                                // ⭐ OPTION 1: Block Mismatch
                                // If the user clicked "Job Seeker" but backend says "Employer", stop them.
                                if (chosenRole != null && chosenRole != apiRole) {
                                    Toast.makeText(
                                        context,
                                        "Access Denied: This account is a '$apiRole', but you are trying to login as '$chosenRole'.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return
                                }

                                // ✅ Save session (Role matches)
                                prefManager.saveToken(token)
                                prefManager.setRole(apiRole)
                                if (!name.isNullOrBlank()) prefManager.setUserName(name)
                                prefManager.setLoggedIn(true)

                                // Navigate based on API role to correct ROOT
                                val destination = when (apiRole) {
                                    "employer" -> NavRoutes.EMPLOYER_ROOT
                                    else -> NavRoutes.SEEKER_ROOT
                                }

                                navController.navigate(destination) {
                                    popUpTo(NavRoutes.LOGIN) { inclusive = true }
                                }

                            } else {
                                Log.e("LOGIN_TEST", "Login failed: ${body?.message ?: "Unknown error"}")
                                Toast.makeText(
                                    context,
                                    body?.message ?: "Login failed",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            isLoading = false
                            Log.e("LOGIN_TEST", "Login failed: ${t.localizedMessage}")
                            Toast.makeText(
                                context,
                                "Network error: ${t.localizedMessage}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
            }
        ) {
            Text(if (isLoading) "Logging in..." else "Login")
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = { navController.navigate(NavRoutes.REGISTER) }
        ) {
            Text("New user? Register")
        }
    }
}
