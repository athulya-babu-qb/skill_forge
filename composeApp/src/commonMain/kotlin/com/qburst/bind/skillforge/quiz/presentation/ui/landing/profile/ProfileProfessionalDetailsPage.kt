package com.qburst.bind.skillforge.quiz.presentation.ui.landing.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qburst.bind.skillforge.quiz.presentation.theme.PrimaryColor
import com.qburst.bind.skillforge.quiz.presentation.ui.landing.util.BottomNavigationScreen

@Composable
fun ProfileProfessionalDetailsPage(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val userProfile by profileViewModel.userProfile.collectAsState()

    var designation by remember { mutableStateOf(userProfile.designation) }
    var domain by remember { mutableStateOf(userProfile.domain) }
    var years by remember { mutableStateOf(userProfile.years) }
    var months by remember { mutableStateOf(userProfile.months) }
    var skills by remember { mutableStateOf(userProfile.skills) }

    // Error states
    var experienceError by remember { mutableStateOf(false) }
    var designationError by remember { mutableStateOf(false) }
    var domainError by remember { mutableStateOf(false) }

    fun validateFields(): Boolean {
        // Experience must have at least one non-zero value
        experienceError = (years == 0 && months == 0)
        designationError = designation.isBlank()
        domainError = domain.isBlank()

        return !(experienceError || designationError || domainError)
    }

    Scaffold(
        topBar = {
            CommonTopAppBar(
                name = "skillForge",
                onBackClick = { navController.popBackStack() },
                showBack = true
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(30.dp, 20.dp, 30.dp, 0.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                ProfilePageHeading()

                Spacer(modifier = Modifier.height(20.dp))

                Text("Professional Details", fontWeight = FontWeight.Bold)
                Divider(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(top = 2.dp),
                    color = Color(0xFF6A1B9A),
                    thickness = 2.dp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.wrapContentSize().padding(0.dp, 5.dp)) {
                    Text(text = "Experience")
                    Text(text = "*", color = Color.Red)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    NumberInputField(value = years, onValueChange = { years = it }, label = "Years")
                    Spacer(modifier = Modifier.width(16.dp))
                    NumberInputField(value = months, onValueChange = { months = it }, label = "Months")
                }
                if (experienceError) {
                    Text("Please enter experience in years or months", color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextFieldWithAsterisk(
                    label = "Designation",
                    value = designation,
                    isNumberField = false,
                    onValueChange = { designation = it }
                )
                if (designationError) {
                    Text("Please enter your designation", color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextFieldWithAsterisk(
                    label = "Domain",
                    value = domain,
                    isNumberField = false,
                    onValueChange = { domain = it }
                )
                if (domainError) {
                    Text("Please enter your domain", color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text("Skills", fontWeight = FontWeight.Medium)
                SkillChipsSection(skills = skills, onRemoveSkill = { removedSkill ->
                    skills = skills - removedSkill
                })

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        if (validateFields()) {
                            profileViewModel.updateDesignation(designation)
                            profileViewModel.updateDomain(domain)
                            profileViewModel.updateExperience(years, months)
                            profileViewModel.updateSkills(skills)
                            navController.navigate(BottomNavigationScreen.Profile.route)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                    modifier = Modifier
                        .height(40.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Save", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    )
}

@Composable
fun NumberInputField(value: Int, onValueChange: (Int) -> Unit, label: String) {

    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = if (!isFocused && value == 0) "0" else if (value == 0) "" else value.toString(),
        onValueChange = { newValue ->
            val filteredValue = newValue.filter { it.isDigit() }
            onValueChange(filteredValue.toIntOrNull() ?: 0)
        },
        label = { Text(label) },
        modifier = Modifier
            .width(100.dp)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
                if (!focusState.isFocused && value == 0) {
                    onValueChange(0)
                }
            },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun SkillChipsSection(
    skills: List<String>,
    onRemoveSkill: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray),
        color = Color.Transparent
    ) {
        FlowRow(
            modifier = Modifier
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            skills.forEach { skill ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.LightGray
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(skill)
                        Spacer(modifier = Modifier.width(4.dp))
                        IconButton(
                            onClick = { onRemoveSkill(skill) },
                            modifier = Modifier.size(20.dp)
                        ) {
                            //Need to add close icon
//                            Icon(
//                                imageVector = Icons.Default.Close,
//                                contentDescription = "Remove",
//                                modifier = Modifier.size(16.dp)
//                            )
                        }
                    }
                }
            }
        }
    }
}
