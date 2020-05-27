package com.henrynam.mynote.utils


import androidx.fragment.app.FragmentTransaction
import com.henrynam.mynote.R
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment


fun DaggerFragment.switchFragment( fragment: DaggerFragment, replace:Int = R.id.frameContent ){
    val name: String = fragment.javaClass.name
    val ft : FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
    ft.replace(replace, fragment)
    ft.addToBackStack(name)
    ft.commit()
}

fun DaggerAppCompatActivity.switchFragment(fragment: DaggerFragment, replace:Int = R.id.frameContent ){
    val name: String = fragment.javaClass.name
    val ft : FragmentTransaction = supportFragmentManager.beginTransaction()
    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
    ft.replace(replace, fragment)
    ft.addToBackStack(name)
    ft.commit()
}





