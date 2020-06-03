package com.henrynam.mynote.presentation.authenication.forgotpass

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.henrynam.mynote.R
import com.henrynam.mynote.utils.isEmailValid
import javax.inject.Inject

class ForgotPasswordViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    val success = MutableLiveData<Boolean>()
    val errorMsg = MutableLiveData<Int>()
    val forgotPassWord = ObservableField<String>()

    fun sendForgotPass(){
        if (validateSendForgot(forgotPassWord.get().toString())) {
            auth.sendPasswordResetEmail(forgotPassWord.get().toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        success.postValue(true)
                    }
                }

        }
    }

    private fun validateSendForgot(account: String): Boolean {
        if (account.isEmpty()) {
            errorMsg.postValue(R.string.error_not_enough_email_pass)
            return false
        }

        if (!isEmailValid(email = account)) {
            errorMsg.postValue(R.string.error_not_invalid_email)
            return false
        }

        return true
    }

}