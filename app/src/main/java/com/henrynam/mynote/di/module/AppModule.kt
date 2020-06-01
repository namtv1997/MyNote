package com.henrynam.mynote.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.henrynam.mynote.MainApplication
import com.henrynam.mynote.data.Constants
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

    @Provides
    fun firebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun dbNotes(): DatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.NODE_AUTHORS)

}