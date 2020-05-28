package com.henrynam.mynote.di.module

import com.henrynam.mynote.di.scoped.ActivityScoped
import com.henrynam.mynote.presentation.addnote.AddNodeActivity
import com.henrynam.mynote.presentation.authenication.AuthenticationActivity
import com.henrynam.mynote.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity


    @ActivityScoped
    @ContributesAndroidInjector()
    abstract fun contributeAuthenticationActivity(): AuthenticationActivity

    @ActivityScoped
    @ContributesAndroidInjector()
    abstract fun contributeAddNodeActivity(): AddNodeActivity

}