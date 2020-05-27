package com.henrynam.mynote.presentation.authenication.signin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

import com.henrynam.mynote.R
import com.henrynam.mynote.databinding.FragmentSignInBinding
import com.henrynam.mynote.presentation.authenication.forgotpass.ForgotPasswordFragment
import com.henrynam.mynote.presentation.authenication.signup.SignUpFragment
import com.henrynam.mynote.presentation.base.BaseFragment
import com.henrynam.mynote.presentation.main.MainActivity
import com.henrynam.mynote.utils.switchFragment
import javax.inject.Inject

class SignInFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth

    private val viewModel: SignInViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(SignInViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {
            val email: String = binding.edtAccount.getText()
            val password: String = binding.edtPassword.getText()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(activity, R.string.error_not_enough_email_pass, Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(activity, MainActivity::class.java))
                        binding.tvlabelError.visibility = View.GONE
                    }else{
                        binding.tvlabelError.visibility = View.VISIBLE
                    }
                }
        }

        binding.btnRegister.setOnClickListener {
            switchFragment(SignUpFragment())
        }

        binding.btnForgotPassword.setOnClickListener {
            switchFragment(ForgotPasswordFragment())
        }
    }
}
