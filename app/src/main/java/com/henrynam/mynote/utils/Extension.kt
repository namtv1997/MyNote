package com.henrynam.mynote.utils


import androidx.fragment.app.FragmentTransaction
import com.henrynam.mynote.R
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import java.util.regex.Matcher
import java.util.regex.Pattern

const val EMAIL_REGEX = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

fun isEmailValid(email: String) = Pattern.matches(EMAIL_REGEX, email)


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






