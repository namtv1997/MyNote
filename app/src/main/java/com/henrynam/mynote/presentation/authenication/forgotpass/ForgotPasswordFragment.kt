package com.henrynam.mynote.presentation.authenication.forgotpass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.henrynam.mynote.R
import com.henrynam.mynote.databinding.FragmentForgotPasswordBinding
import com.henrynam.mynote.presentation.authenication.signin.SignInFragment
import com.henrynam.mynote.presentation.base.BaseFragment
import com.henrynam.mynote.utils.switchFragment
import javax.inject.Inject

class ForgotPasswordFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentForgotPasswordBinding

    private val viewModel: ForgotPasswordViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ForgotPasswordViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.success.observe(viewLifecycleOwner, Observer {
            if (it){
                Toast.makeText(activity, R.string.label_email_confirm, Toast.LENGTH_LONG).show()
                activity?.onBackPressed()
            }
        })

        viewModel.errorMsg.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()

        })

        binding.btnLoginNow.setOnClickListener {
            switchFragment(SignInFragment())
        }

    }

}
