package com.henrynam.mynote.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.internal.NavigationMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.henrynam.mynote.R
import com.henrynam.mynote.data.Constants
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
    private lateinit var auth: FirebaseAuth

    private val dbAuthors = FirebaseDatabase.getInstance().getReference(Constants.NODE_AUTHORS)
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
        auth = FirebaseAuth.getInstance()
        dbAuthors.keepSynced(true)


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

        fetchAuthors1()
        fetchAuthors()

        if (adapterNote.itemCount == 0) binding.lnEmpty.visibility = View.VISIBLE
        else binding.lnEmpty.visibility = View.GONE
    }


    override fun onItemClick(node: Note) {
        val intent = Intent(this, AddNodeActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("data", node)
        intent.putExtras(bundle)
        startActivityForResult(intent, 2)
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

    private fun fetchAuthors() {
        val dbNoteChild =
            dbAuthors.child(auth.currentUser?.uid.toString()).child(getString(R.string.note_list))
                .orderByChild("pin").equalTo(true)
        dbNoteChild.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val authors = mutableListOf<Note>()
                    for (authorSnapshot in snapshot.children) {
                        val author = authorSnapshot.getValue(Note::class.java)
                        if (author != null) {
                            adapterNote.addNote(0, author)
                        }
                        author?.let { authors.add(it) }
                    }

                    if (authors.size > 0)
                        binding.lnEmpty.visibility = View.GONE
                    else binding.lnEmpty.visibility = View.VISIBLE
                }
            }
        })

    }

    private fun fetchAuthors1() {
        val dbNoteChild =
            dbAuthors.child(auth.currentUser?.uid.toString()).child(getString(R.string.note_list))
                .orderByChild("pin").equalTo(false)
        dbNoteChild.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val authors = mutableListOf<Note>()
                    for (authorSnapshot in snapshot.children) {
                        val author = authorSnapshot.getValue(Note::class.java)

                        author?.let { authors.add(it) }
                        adapterNote.setData(authors)
                    }

                    if (authors.size > 0)
                        binding.lnEmpty.visibility = View.GONE
                    else binding.lnEmpty.visibility = View.VISIBLE
                }
            }
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
