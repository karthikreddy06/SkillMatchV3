package com.simats.SkillMatchV3.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.simats.SkillMatchV3.navigation.NavRoutes
import com.simats.SkillMatchV3.network.ApiClient
import com.simats.SkillMatchV3.network.ApiService
import com.simats.SkillMatchV3.network.RegisterRequest
import com.simats.SkillMatchV3.network.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RegisterScreen(navController: NavHostController) {

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val api = ApiClient.retrofit.create(ApiService::class.java)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Register", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "All fields required", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                isLoading = true

                val request = RegisterRequest(
                    name = name,
                    email = email,
                    phone = phone,
                    password = password
                )

                api.register(request).enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        isLoading = false

                        if (response.isSuccessful && response.body()?.status == true) {
                            Toast.makeText(
                                context,
                                "Registered successfully. Please login.",
                                Toast.LENGTH_LONG
                            ).show()

                            navController.navigate(NavRoutes.LOGIN) {
                                popUpTo(NavRoutes.REGISTER) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                response.body()?.message ?: "Registration failed",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        isLoading = false
                        Toast.makeText(
                            context,
                            t.message ?: "Network error",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Registering..." else "Register")
        }

        TextButton(
            onClick = { navController.navigate(NavRoutes.LOGIN) }
        ) {
            Text("Already have an account? Login")
        }
    }
}
