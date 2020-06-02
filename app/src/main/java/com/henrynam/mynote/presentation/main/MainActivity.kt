package com.henrynam.mynote.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.internal.NavigationMenu
import com.google.firebase.auth.FirebaseAuth
import com.henrynam.mynote.R
import com.henrynam.mynote.data.Constants.DATA
import com.henrynam.mynote.data.Note
import com.henrynam.mynote.databinding.ActivityMainBinding
import com.henrynam.mynote.presentation.addnote.AddNodeActivity
import com.henrynam.mynote.presentation.addnote.NoteAdapter
import com.henrynam.mynote.presentation.authenication.AuthenticationActivity
import com.henrynam.mynote.presentation.base.BaseActivity
import io.github.yavski.fabspeeddial.FabSpeedDial
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), NoteAdapter.NoteAdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterNote: NoteAdapter
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
                        startActivity(Intent(applicationContext, AddNodeActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                }
                return true
            }

            override fun onMenuClosed() {}

        })

    }

    override fun onStart() {
        super.onStart()
        initAdapters()

        viewModel.noteList.observe(this, androidx.lifecycle.Observer {
            adapterNote.setData(it)
            if (it.isNotEmpty()){
                binding.lnEmpty.visibility = View.GONE
                binding.rvNote.visibility = View.VISIBLE
            } else {
                binding.lnEmpty.visibility = View.VISIBLE
                binding.rvNote.visibility = View.GONE
            }
        })

        viewModel.haveData.observe(this, androidx.lifecycle.Observer {
            if (!it) {
                binding.lnEmpty.visibility = View.VISIBLE
                binding.rvNote.visibility = View.GONE
            }else{
                binding.lnEmpty.visibility = View.GONE
                binding.rvNote.visibility = View.VISIBLE
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.title_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchNotes(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchNotes(newText.toString())
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    override fun onItemClick(node: Note) {
        val intent = Intent(this, AddNodeActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(DATA, node)
        intent.putExtras(bundle)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun initAdapters() {
        // Init adapter Suggested
        adapterNote = NoteAdapter(mutableListOf())
        adapterNote.setListener(this)

        binding.rvNote.apply {
            setHasFixedSize(true)
            layoutManager = object : StaggeredGridLayoutManager(2, VERTICAL) {}
            adapter = adapterNote
        }
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
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finishAffinity()
                dialog.dismiss()
            }
            .show()
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}
