package com.henrynam.mynote.presentation.authenication

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthenticationViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    fun checkLogin():Boolean{
        return auth.currentUser != null
    }
}