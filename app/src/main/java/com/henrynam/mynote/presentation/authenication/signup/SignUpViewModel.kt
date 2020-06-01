package com.henrynam.mynote.presentation.authenication.signup

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.henrynam.mynote.R
import com.henrynam.mynote.utils.isEmailValid
import javax.inject.Inject

class SignUpViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    val registerSuccess = MutableLiveData<Boolean>()
    val errorMsg = MutableLiveData<Int>()
    val email = ObservableField<String>()
    val passWord = ObservableField<String>()

    fun register() {
        if (validateRegister(email.get().toString(), passWord.get().toString())) {
            auth.createUserWithEmailAndPassword(email.get().toString(), email.get().toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        registerSuccess.postValue(true)
                    }
                }
        }
    }

    private fun validateRegister(account: String, password: String): Boolean {
        if (account.isEmpty()) {
            errorMsg.postValue(R.string.error_not_enough_email_pass)
            return false
        }

        if (password.isEmpty()) {
            errorMsg.postValue(R.string.error_not_enough_email_pass)
            return false
        }

        if (password.length < 6) {
            errorMsg.postValue(R.string.error_not_enough_character)
            return false
        }

        if (!isEmailValid(email = account)) {
            errorMsg.postValue(R.string.error_not_invalid_email)
            return false
        }

        return true
    }

}