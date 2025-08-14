package com.qburst.bind.skillforge.quiz.presentation.ui.landing.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.qburst.bind.skillforge.quiz.presentation.theme.PrimaryColor
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.icon_profile
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProfileScreen(navController: NavController, profileViewModel: ProfileViewModel = viewModel()) {

    val userProfile by profileViewModel.userProfile.collectAsState()

    Scaffold(modifier = Modifier
        .fillMaxSize(),
        topBar = {
            CommonTopAppBar(name = "skillForge", onBackClick = { navController.navigate("ProfileEditBasicDetails") }, showBack = false)
        },
        content = {it ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(20.dp)
                .background(Color.Transparent)
                .verticalScroll(rememberScrollState())
            ) {

                Button(onClick = {
                        navController.navigate("ProfileEditBasicDetails")
                    },
                        modifier = Modifier
                            .width(80.dp)
                            .height(40.dp)
                            .align(Alignment.End),
                        colors = ButtonDefaults.buttonColors(PrimaryColor),
                        shape = RoundedCornerShape(10.dp)

                    ) {
                        Text(text = "Edit", color = Color.White , fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    }

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterHorizontally)
                        .padding(0.dp, 20.dp, 0.dp, 20.dp),

                    ) {
                    Box( modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .background(color = Color.LightGray)
                        .border(5.dp, Color.Gray, shape = CircleShape),
                        contentAlignment = Alignment.Center,

                        ){
                        Image(painter = painterResource(Res.drawable.icon_profile),
                            contentDescription = "Profile Placeholder",
                            modifier = Modifier.size(180.dp))
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = (-8).dp, y = (-6).dp) // move icon slightly inside the circle
                            .size(30.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White, shape = CircleShape)
                            .border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = {
                            navController.navigate("ProfileEditBasicDetails")
                        }) {
//                            // Need to add edit icon for profile picture
//                            Icon(
//                                painter = painterResource(),
//                                contentDescription = "Edit Icon",
//
//                                tint = Color.Black,
//                                modifier = Modifier.size(20.dp)
//                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    UserProfile("${userProfile.firstName} ${userProfile.lastName}", 20.sp, FontWeight.SemiBold)
                    UserProfile("Employer Id : 1234", 18.sp, FontWeight.Normal)
                    UserProfile("India - Bangalore", 18.sp, FontWeight.Normal)
                }

                Text(text = "About", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp, 40.dp, 40.dp, 20.dp))

                Column(modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(40.dp, 10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    ProfileDetailRow(label = "Mobile:", value = "${userProfile.phoneNo}")
                    ProfileDetailRow(label = "Experience:", value = "${userProfile.years} Years ${userProfile.months} Month")
                    ProfileDetailRow(label = "Designation:", value = userProfile.designation)
                    ProfileDetailRow(label = "Domain:", value = userProfile.domain)
                }
            }
        }
    )
}

@Composable
fun UserProfile(Name : String, size : TextUnit, fontWeight: FontWeight) {
    Column {
        Text(text = Name, fontSize = size, fontWeight = fontWeight, modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp))
    }
}

@Composable
fun ProfileDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .weight(1f)
                .padding(10.dp, 0.dp)
        )
        Text(
            text = value,
            modifier = Modifier
                .padding(10.dp, 0.dp)
                .weight(1f),
            fontSize = 16.sp,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(name: String, onBackClick : () -> Unit, showBack: Boolean) {
    val topBarBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    TopAppBar(
        title = {
            Text(
                text = "skillForge",
                textAlign = TextAlign.Left,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(0.dp, 10.dp)
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = PrimaryColor,
            titleContentColor = Color.White,
        ),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        navigationIcon = {
            // Need to add back arrow icon
            if (showBack) {
//                IconButton(onClick = onBackClick) {
//                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
//                }
            }
        },
        scrollBehavior = topBarBehavior
    )
}
