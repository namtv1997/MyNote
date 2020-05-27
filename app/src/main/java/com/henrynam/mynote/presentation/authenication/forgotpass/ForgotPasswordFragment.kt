package com.henrynam.mynote.presentation.authenication.forgotpass

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth

import com.henrynam.mynote.R
import com.henrynam.mynote.databinding.FragmentForgotPasswordBinding
import com.henrynam.mynote.presentation.authenication.signin.SignInFragment
import com.henrynam.mynote.presentation.base.BaseFragment
import com.henrynam.mynote.utils.switchFragment
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

class ForgotPasswordFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    private val viewModel: ForgotPasswordViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ForgotPasswordViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnSend.setOnClickListener {
            val email: String = binding.edtEmail.getText()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(activity, R.string.error_not_enough_email_pass, Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            if (!isValidEmail(email)){
                Toast.makeText(activity, R.string.error_not_invalid_email, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(activity, R.string.label_email_confirm, Toast.LENGTH_LONG).show()
                        activity?.onBackPressed()
                    }
                }
        }

        binding.btnLoginNow.setOnClickListener {
            switchFragment(SignInFragment())
        }
    }

    private fun isValidEmail(email: String?): Boolean {
        if (email != null) {
            val p: Pattern = Pattern.compile("^[A-Za-z].*?@gmail\\.com$")
            val m: Matcher = p.matcher(email)
            return m.find()
        }
        return false
    }

}
