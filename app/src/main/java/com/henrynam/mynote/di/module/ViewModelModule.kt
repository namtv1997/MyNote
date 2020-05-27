package com.henrynam.mynote.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.henrynam.mynote.di.viewmodel.ViewModelFactory
import com.henrynam.mynote.di.viewmodel.ViewModelKey
import com.henrynam.mynote.presentation.authenication.AuthenticationViewModel
import com.henrynam.mynote.presentation.authenication.forgotpass.ForgotPasswordViewModel
import com.henrynam.mynote.presentation.authenication.signin.SignInViewModel
import com.henrynam.mynote.presentation.authenication.signup.SignUpViewModel
import com.henrynam.mynote.presentation.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthenticationViewModel::class)
    abstract fun authenticationViewModel(authenticationViewModel: AuthenticationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun signUpViewModel(signUpViewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun signInViewModel(signInViewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForgotPasswordViewModel::class)
    abstract fun forgotPasswordViewModel(forgotPasswordViewModel: ForgotPasswordViewModel): ViewModel
}