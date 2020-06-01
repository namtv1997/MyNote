package com.henrynam.mynote.presentation.authenication.signin

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.henrynam.mynote.R
import com.henrynam.mynote.utils.isEmailValid
import javax.inject.Inject

class SignInViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    val loginSuccess = MutableLiveData<Boolean>()
    val errorMsg = MutableLiveData<Int>()
    val email = ObservableField<String>()
    val passWord = ObservableField<String>()

    fun login() {
        if (validateLogin(email.get().toString(), passWord.get().toString())) {
            auth.signInWithEmailAndPassword(email.get().toString(), passWord.get().toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        loginSuccess.postValue(true)
                    }else{
                        loginSuccess.postValue(true)
                    }
                }

        }
    }

    private fun validateLogin(account: String, password: String): Boolean {
        if (account.isEmpty()) {
            errorMsg.postValue(R.string.error_not_enough_email_pass)
            return false
        }

        if (password.isEmpty()) {
            errorMsg.postValue(R.string.error_not_enough_email_pass)
            return false
        }

        return true
    }
}