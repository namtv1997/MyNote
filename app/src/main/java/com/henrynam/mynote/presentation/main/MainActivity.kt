package com.henrynam.mynote.presentation.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.internal.NavigationMenu
import com.google.firebase.auth.FirebaseAuth
import com.henrynam.mynote.R
import com.henrynam.mynote.databinding.ActivityMainBinding
import com.henrynam.mynote.presentation.authenication.AuthenticationActivity
import com.henrynam.mynote.presentation.base.BaseActivity
import io.github.yavski.fabspeeddial.FabSpeedDial
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)

        binding.fabMain.setMenuListener(object : FabSpeedDial.MenuListener {
            override fun onPrepareMenu(p0: NavigationMenu?): Boolean {
                return true
            }

            override fun onMenuItemSelected(menuItem: MenuItem?): Boolean {
                when (menuItem?.itemId) {
                    R.id.ic_logout -> {
                        logout()
                    }

                    R.id.ic_create -> {
                    }
                }
                return true
            }

            override fun onMenuClosed() {}

        })


    }

    private fun logout() {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.label_confirm_logout))
            .setNegativeButton(getString(R.string.label_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.label_accept)) { dialog, _ ->
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, AuthenticationActivity::class.java)
                startActivity(intent)
                finishAffinity()
                dialog.dismiss()
            }
            .show()
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
