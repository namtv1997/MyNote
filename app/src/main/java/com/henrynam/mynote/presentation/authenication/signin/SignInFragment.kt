package com.henrynam.mynote.presentation.authenication.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.henrynam.mynote.R
import com.henrynam.mynote.databinding.FragmentSignInBinding
import com.henrynam.mynote.presentation.authenication.signup.SignUpFragment
import com.henrynam.mynote.presentation.base.BaseFragment
import com.henrynam.mynote.presentation.main.MainActivity
import com.henrynam.mynote.utils.switchFragment
import javax.inject.Inject

class SignInFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSignInBinding

    private val viewModel: SignInViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(SignInViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            switchFragment(SignUpFragment())
        }
    }
}
