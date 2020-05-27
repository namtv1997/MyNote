package com.henrynam.mynote.presentation.authenication.signup

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
    private lateinit var auth: FirebaseAuth

    private val viewModel: SignUpViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            switchFragment(SignInFragment())
        }

        binding.btnSignUp.setOnClickListener {
            val email: String = binding.edtAccount.getText()
            val password: String = binding.edtPassword.getText()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(activity, R.string.error_not_enough_email_pass, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(activity, R.string.error_not_enough_character, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (!isValidEmail(email)){
                Toast.makeText(activity, R.string.error_not_invalid_email, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(activity, R.string.label_register_success, Toast.LENGTH_LONG).show()
                    }
                }
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
