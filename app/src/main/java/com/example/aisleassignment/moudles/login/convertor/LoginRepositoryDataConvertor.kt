package com.example.aisleassignment.moudles.login.convertor

import android.provider.ContactsContract
import com.example.aisleassignment.moudles.core.utils.Resource
import com.example.aisleassignment.moudles.login.models.network.OtpValidationResponse
import com.example.aisleassignment.moudles.login.models.network.PhoneLoginResponse
import com.example.aisleassignment.moudles.notes.models.network.UserDataResponse
import com.example.aisleassignment.moudles.notes.models.ui.Likes
import com.example.aisleassignment.moudles.notes.models.ui.LikesData
import com.example.aisleassignment.moudles.notes.models.ui.Notes
import com.example.aisleassignment.moudles.notes.models.ui.Profile
import com.example.aisleassignment.moudles.notes.ui.adapter.NotesAdapter.Companion.HEADER_TEXT
import com.example.aisleassignment.moudles.notes.ui.adapter.NotesAdapter.Companion.PROFILE
import com.example.aisleassignment.moudles.notes.ui.adapter.NotesAdapter.Companion.SUGGESTION
import com.example.aisleassignment.moudles.notes.ui.adapter.NotesAdapter.Companion.UPGRADE
import java.lang.Exception

class LoginRepositoryDataConvertor {
    fun loginUsingPhoneNumber(api: PhoneLoginResponse): Resource<Boolean> {
        return Resource.success(api.status)
    }

    fun validateOtp(validateOtp: OtpValidationResponse): Resource<String> {
        if (validateOtp.token.isNullOrEmpty()) {
            return Resource.error("Error validating OTP.")
        }
        return Resource.success(validateOtp.token)
    }

    fun getUserData(userData: UserDataResponse): Resource<ArrayList<Notes>> {
        try {
            val list = arrayListOf<Notes>()
            list.add(Notes(HEADER_TEXT))
            val profile = Profile(PROFILE)
            profile.imgSource = userData.invites.profiles[0].photos[0].photo
            profile.nameAge = userData.invites.profiles[0].general_information.first_name
            list.add(profile)
            list.add(Notes(UPGRADE))
            val likes = Likes(SUGGESTION)
            val l = userData.likes.profiles.map {
                LikesData(
                    it.first_name,
                    it.avatar
                )
            }
            likes.listOfLikes.addAll(l)
            likes.listOfLikes.addAll(l)
            likes.listOfLikes.addAll(l)
            list.add(likes)
            return Resource.success(list)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.error(e.message.toString())
        }
    }
}