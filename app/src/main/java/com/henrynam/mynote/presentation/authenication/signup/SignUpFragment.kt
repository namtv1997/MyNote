package com.henrynam.mynote.presentation.authenication.signup

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.henrynam.mynote.R
import com.henrynam.mynote.databinding.FragmentSignUpBinding
import com.henrynam.mynote.presentation.authenication.signin.SignInFragment
import com.henrynam.mynote.presentation.base.BaseFragment
import com.henrynam.mynote.presentation.main.MainActivity
import com.henrynam.mynote.utils.switchFragment
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

class SignUpFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSignUpBinding

    private val viewModel: SignUpViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.registerSuccess.observe(viewLifecycleOwner, Observer {
            if (it){
                startActivity(Intent(activity, MainActivity::class.java))

                Toast.makeText(activity, R.string.label_register_success, Toast.LENGTH_LONG).show()
            }

        })

        viewModel.errorMsg.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()

        })

        binding.btnLogin.setOnClickListener {
            switchFragment(SignInFragment())
        }

    }

}
