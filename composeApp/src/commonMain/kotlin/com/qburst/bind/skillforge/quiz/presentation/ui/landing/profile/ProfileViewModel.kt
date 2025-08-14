package com.qburst.bind.skillforge.quiz.presentation.ui.landing.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class UserProfile(
    var firstName: String = "Qburst",
    var lastName: String = "User",
    var email: String = "qb.user@gmail.com",
    var phoneNo: Long = 9090909090,
    var years: Int = 3,
    var months: Int = 6,
    var designation: String = "Senior Engineer",
    var domain: String = ".Net",
    var skills: List<String> = listOf("Kotlin", "Java", "Android Development", "Flutter", "React Native")
)

class ProfileViewModel : ViewModel() {
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile

    fun updateFirstName(firstName: String) {
        _userProfile.value = _userProfile.value.copy(firstName = firstName)
    }

    fun updateLastName(lastName: String) {
        _userProfile.value = _userProfile.value.copy(lastName = lastName)
    }

    fun updateDesignation(designation: String) {
        _userProfile.value = _userProfile.value.copy(designation = designation)
    }

    fun updateDomain(domain: String) {
        _userProfile.value = _userProfile.value.copy(domain = domain)
    }

    fun updateExperience(years: Int, months: Int) {
        _userProfile.value = _userProfile.value.copy(years = years, months = months)
    }

    fun updateEmail(email: String) {
        _userProfile.value = _userProfile.value.copy(email = email)
    }

    fun updatePhoneNo(phoneNo: Long) {
        _userProfile.value = _userProfile.value.copy(phoneNo = phoneNo)
    }

    fun updateSkills(skills: List<String>) {
        _userProfile.value = _userProfile.value.copy(skills = skills)
    }
}
