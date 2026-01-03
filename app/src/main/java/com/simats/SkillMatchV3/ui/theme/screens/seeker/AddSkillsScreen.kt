package com.simats.SkillMatchV3.ui.theme.screens.seeker

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.SkillMatchV3.network.ApiClient
import com.simats.SkillMatchV3.network.ApiService
import com.simats.SkillMatchV3.network.GenericResponse
import com.simats.SkillMatchV3.network.Skill
import com.simats.SkillMatchV3.network.SkillsListResponse
import com.simats.SkillMatchV3.network.UpdateSkillsRequest
import com.simats.SkillMatchV3.network.AddSkillRequest
import com.simats.SkillMatchV3.network.AddSkillResponse
import com.simats.SkillMatchV3.utils.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddSkillsScreen(navController: NavController) {
    val context = LocalContext.current
    val prefManager = remember { PrefManager(context) }
    
    var allSkills by remember { mutableStateOf<List<Skill>>(emptyList()) }
    val selectedSkills = remember { mutableStateListOf<String>() }
    val selectedSkillIds = remember { mutableStateListOf<Int>() }
    
    var searchText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var isSaving by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val token = prefManager.getToken() ?: return@LaunchedEffect
        val apiService = ApiClient.retrofit.create(ApiService::class.java)

        apiService.getSkills().enqueue(object : Callback<SkillsListResponse> {
            override fun onResponse(call: Call<SkillsListResponse>, response: Response<SkillsListResponse>) {
                if (response.isSuccessful && response.body()?.status == true) {
                    allSkills = response.body()!!.skills
                }
            }
            override fun onFailure(call: Call<SkillsListResponse>, t: Throwable) {
                Log.e("SKILLS_API_FAILURE", "Failed to load skills: ${t.message}")
            }
        })

        apiService.getUserSkills("Bearer $token").enqueue(object : Callback<SkillsListResponse> {
            override fun onResponse(call: Call<SkillsListResponse>, response: Response<SkillsListResponse>) {
                if (response.isSuccessful && response.body()?.status == true) {
                    val userSkills = response.body()!!.skills
                    selectedSkills.clear()
                    selectedSkillIds.clear()
                    userSkills.forEach { 
                        selectedSkills.add(it.name)
                        selectedSkillIds.add(it.id)
                    }
                }
                isLoading = false
            }
            override fun onFailure(call: Call<SkillsListResponse>, t: Throwable) {
                Log.e("SKILLS_API_FAILURE", "Failed to load user skills: ${t.message}")
                isLoading = false
            }
        })
    }

    fun addNewSkillToBackend(skillName: String) {
        val token = prefManager.getToken() ?: return
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        
        apiService.addSkill("Bearer $token", AddSkillRequest(skillName)).enqueue(object : Callback<AddSkillResponse> {
            override fun onResponse(call: Call<AddSkillResponse>, response: Response<AddSkillResponse>) {
                if (response.isSuccessful) {
                     val body = response.body()
                     if (body != null && body.status) {
                         val skill = body.skill
                         if (!selectedSkillIds.contains(skill.id)) {
                             selectedSkills.add(skill.name)
                             selectedSkillIds.add(skill.id)
                         }
                         if (allSkills.find { it.id == skill.id } == null) {
                             allSkills = allSkills + skill
                         }
                         searchText = ""
                     }
                }
            }
            override fun onFailure(call: Call<AddSkillResponse>, t: Throwable) {
                 Log.e("ADD_SKILL_FAILURE", "Failed to add skill: ${t.message}")
            }
        })
    }

    fun addSkill(skill: Skill) {
        if (!selectedSkillIds.contains(skill.id)) {
            selectedSkills.add(skill.name)
            selectedSkillIds.add(skill.id)
        }
        searchText = ""
    }

    fun removeSkill(skillName: String) {
        val skillObj = allSkills.find { it.name.equals(skillName, ignoreCase = true) }
        selectedSkills.remove(skillName)
        if (skillObj != null) {
            selectedSkillIds.remove(skillObj.id)
        }
    }
    
    fun saveSkillsToBackend() {
        val token = prefManager.getToken() ?: return
        isSaving = true
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        
        val idsToSend = selectedSkillIds.toList()

        Log.d("SKILLS_SAVE_PAYLOAD", "Sending skill IDs = ${idsToSend.joinToString()}")
        
        val request = UpdateSkillsRequest(skills = idsToSend)
        
        apiService.updateSkills("Bearer $token", request).enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                isSaving = false
                if (response.isSuccessful && response.body()?.status == true) {
                    Log.d("SKILLS_SAVE_SUCCESS", "Skills updated successfully")
                    navController.previousBackStackEntry?.savedStateHandle?.set("skills_updated", true)
                    navController.popBackStack()
                } else {
                    Log.e("SKILLS_SAVE_FAILURE", "Failed to save: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                isSaving = false
                Log.e("SKILLS_SAVE_FAILURE", "Network error: ${t.message}")
            }
        })
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Skills", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            val suggestions = if (searchText.isBlank()) emptyList() else allSkills.filter { 
                it.name.contains(searchText, ignoreCase = true) && !selectedSkillIds.contains(it.id)
            }

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search skills...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )
            
            if (searchText.isNotBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth().heightIn(max = 200.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    LazyColumn {
                        if (suggestions.isEmpty()) {
                            item {
                                ListItem(
                                    headlineContent = { Text("Add '$searchText'") },
                                    leadingContent = { Icon(Icons.Default.Add, contentDescription = null) },
                                    modifier = Modifier.clickable { addNewSkillToBackend(searchText) }
                                )
                            }
                        } else {
                            items(suggestions) { skill ->
                                ListItem(
                                    headlineContent = { Text(skill.name) },
                                    modifier = Modifier.clickable { addSkill(skill) }
                                )
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text("Your Skills", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else if (selectedSkills.isEmpty()) {
                Text("No skills added yet.", color = Color.Gray)
            } else {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selectedSkills.forEach { skill ->
                        InputChip(
                            selected = true,
                            onClick = { removeSkill(skill) },
                            label = { Text(skill) },
                            trailingIcon = {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Remove",
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            colors = InputChipDefaults.inputChipColors(
                                selectedContainerColor = Color(0xFFE3F2FD),
                                selectedLabelColor = Color(0xFF0D47A1)
                            )
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text("Popular Skills", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            
            val popularSuggestions = allSkills.filter { !selectedSkillIds.contains(it.id) }.take(10)
            
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                popularSuggestions.forEach { skill ->
                     SuggestionChip(
                        onClick = { addSkill(skill) },
                        label = { Text(skill.name) },
                        icon = { Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = {
                    if (isSaving) return@Button
                    saveSkillsToBackend()
                },
                enabled = !isSaving && selectedSkillIds.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066FF))
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Save Skills", fontSize = 16.sp)
                }
            }
        }
    }
}
