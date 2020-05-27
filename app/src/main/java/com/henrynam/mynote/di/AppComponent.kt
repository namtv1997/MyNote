package com.henrynam.mynote.di

import com.henrynam.mynote.MainApplication
import com.henrynam.mynote.di.module.ActivityModule
import com.henrynam.mynote.di.module.AppModule
import com.henrynam.mynote.di.module.FragmentModule
import com.henrynam.mynote.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ViewModelModule::class,
    FragmentModule::class,
    AppModule::class,
    ActivityModule::class
])

interface AppComponent : AndroidInjector<MainApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: MainApplication): AppComponent
    }
}
