package com.henrynam.mynote.di.module

import com.henrynam.mynote.MainApplication
import com.henrynam.mynote.utils.SharePrefs
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideSharedPreference(application: MainApplication) : SharePrefs {
        return SharePrefs(application)
    }
}