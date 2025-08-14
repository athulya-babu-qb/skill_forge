package com.qburst.bind.skillforge.quiz.presentation.ui.landing.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.qburst.bind.skillforge.quiz.presentation.theme.PrimaryColor

@Composable
fun ProfileBasicDetailsScreen(navController: NavController, profileViewModel: ProfileViewModel = viewModel()) {
    val userProfile by profileViewModel.userProfile.collectAsState()

    var firstName by remember { mutableStateOf(userProfile.firstName) }
    var lastName by remember { mutableStateOf(userProfile.lastName) }
    var userEmail by remember { mutableStateOf(userProfile.email) }
    var userPhoneNo by remember { mutableStateOf(if (userProfile.phoneNo == 0L) "" else userProfile.phoneNo.toString()) }

    var firstNameError by remember { mutableStateOf(false) }
    var lastNameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        return email.matches(emailRegex.toRegex())
    }

    fun validateFields(): Boolean {
        firstNameError = firstName.isBlank()
        lastNameError = lastName.isBlank()
        emailError = !isValidEmail(userEmail)
        userPhoneNo = userPhoneNo.filter { it.isDigit() }

        return !(firstNameError || lastNameError || emailError || phoneError)
    }

    Scaffold(
        topBar = {
            CommonTopAppBar(
                name = "skillForge",
                onBackClick = { navController.popBackStack() },
                showBack = true
            )
        },
        content = { it ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(30.dp, 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                ProfilePageHeading()
                Spacer(modifier = Modifier.height(24.dp))

                Text("Basic Details", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Divider(
                    modifier = Modifier.width(120.dp).padding(top = 2.dp),
                    color = Color(0xFF6A1B9A),
                    thickness = 2.dp
                )
                Spacer(modifier = Modifier.height(24.dp))

                TextFieldWithAsterisk(
                    label = "First Name",
                    value = firstName,
                    onValueChange = { firstName = it }
                )
                if (firstNameError) Text("Please enter your First Name", color = Color.Red, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(16.dp))

                TextFieldWithAsterisk(
                    label = "Last Name",
                    value = lastName,
                    onValueChange = { lastName = it }
                )
                if (lastNameError) Text("Please enter your Last Name", color = Color.Red, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(16.dp))

                TextFieldWithAsterisk(
                    label = "Email",
                    value = userEmail,
                    onValueChange = { userEmail = it }
                )
                if (emailError) Text("Please enter valid email address", color = Color.Red, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(16.dp))

                TextFieldWithAsterisk(
                    label = "Mobile",
                    value = userPhoneNo,
                    onValueChange = { userPhoneNo = it }
                )
                if (phoneError) Text("Phone number must be 10 digits", color = Color.Red, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (validateFields()) {
                            profileViewModel.updateFirstName(firstName)
                            profileViewModel.updateLastName(lastName)
                            profileViewModel.updateEmail(userEmail)
                            profileViewModel.updatePhoneNo(userPhoneNo.toLong())
                            navController.navigate("profileEditProfessionalDetails")
                        }
                    },
                    modifier = Modifier
                        .width(90.dp)
                        .height(42.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(PrimaryColor),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Text("Next", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    )
}

@Composable
fun TextFieldWithAsterisk(
    label: String,
    value: String,
    isNumberField: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.wrapContentSize()) {
        Row(modifier = Modifier.wrapContentSize().padding(0.dp, 5.dp)) {
            Text(text = label)
            Text(text = "*", color = Color.Red)
        }

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(),
            keyboardOptions = if (isNumberField) {
                KeyboardOptions(keyboardType = KeyboardType.Number)
            } else {
                KeyboardOptions.Default
            },
        )
    }
}

@Composable
fun ProfilePageHeading() {
    Column(modifier = Modifier.wrapContentSize()) {
        Text(
            buildAnnotatedString {
                append("Hi ")
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF6A1B9A),
                        fontStyle = FontStyle.Italic
                    )
                ) {
                    append("User")
                }
                append(",")
            },
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Please complete your profile",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
