package com.simats.SkillMatchV3.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

                val api = ApiClient.retrofit.create(ApiService::class.java)

                api.login(LoginRequest(email, password))
                    .enqueue(object : Callback<LoginResponse> {

                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            isLoading = false
                            val body = response.body()

                            if (response.isSuccessful && body?.status == true) {

                                val token = body.access_token
                                val role = body.user?.role
                                val name = body.user?.name

                                if (token.isNullOrBlank() || role.isNullOrBlank() || name.isNullOrBlank()) {
                                    Toast.makeText(
                                        context,
                                        "Invalid login response",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return
                                }

                                // ✅ Save session
                                prefManager.saveToken(token)
                                prefManager.setRole(role)
                                prefManager.setUserName(name)
                                prefManager.setLoggedIn(true)

                                // ✅ IMPORTANT FIX: Navigate to ROOT, not HOME
                                val destination = if (role == "employer") {
                                    NavRoutes.EMPLOYER_HOME
                                } else {
                                    NavRoutes.SEEKER_ROOT
                                }

                                navController.navigate(destination) {
                                    popUpTo(NavRoutes.LOGIN) { inclusive = true }
                                }

                            } else {
                                Toast.makeText(
                                    context,
                                    body?.message ?: "Login failed",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            isLoading = false
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
