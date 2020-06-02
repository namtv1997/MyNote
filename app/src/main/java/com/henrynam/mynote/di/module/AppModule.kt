package com.henrynam.mynote.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.henrynam.mynote.data.Constants
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun firebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun dbNotes(): DatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.NODE_AUTHORS)

}