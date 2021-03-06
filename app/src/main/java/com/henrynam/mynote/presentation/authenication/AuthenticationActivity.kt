package com.henrynam.mynote.presentation.authenication

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.henrynam.mynote.R
import com.henrynam.mynote.databinding.ActivityAuthenticationBinding
import com.henrynam.mynote.presentation.authenication.signin.SignInFragment
import com.henrynam.mynote.presentation.base.BaseActivity
import com.henrynam.mynote.presentation.main.MainActivity
import com.henrynam.mynote.utils.switchFragment
import javax.inject.Inject

class AuthenticationActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityAuthenticationBinding

    private val viewModel: AuthenticationViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AuthenticationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication)
        binding.viewModel = viewModel

        if (viewModel.checkLogin()){
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }else{
            switchFragment(SignInFragment())
        }

    }

    override fun onBackPressed() {
        try {
            val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.frameContent)
            if (fragment is SignInFragment) {
                super.onBackPressed()
                finishAffinity()
            } else {
                val nameClass: String = fragment?.javaClass!!.name
                supportFragmentManager.popBackStack(nameClass, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        } catch (e: Exception) {
            print(e.printStackTrace())
            finishAffinity()
        }
    }
}
