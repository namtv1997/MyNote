package com.henrynam.mynote.di.module

import com.henrynam.mynote.di.scoped.FragmentScoped
import com.henrynam.mynote.presentation.authenication.forgotpass.ForgotPasswordFragment
import com.henrynam.mynote.presentation.authenication.signin.SignInFragment
import com.henrynam.mynote.presentation.authenication.signup.SignUpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScoped
    @ContributesAndroidInjector()
    abstract fun contributeSignUpFragment(): SignUpFragment

    @FragmentScoped
    @ContributesAndroidInjector()
    abstract fun contributeSignInFragment(): SignInFragment

    @FragmentScoped
    @ContributesAndroidInjector()
    abstract fun contributeForgotPasswordFragment(): ForgotPasswordFragment
}