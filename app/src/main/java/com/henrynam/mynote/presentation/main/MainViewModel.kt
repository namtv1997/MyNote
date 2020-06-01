package com.henrynam.mynote.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.henrynam.mynote.data.Note
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbNotes: DatabaseReference
) : ViewModel() {

     val note = MutableLiveData<Note>()
     val noteList = MutableLiveData<List<Note>>()

    init {
        dbNotes.keepSynced(true)
//        loadNotesPined()
//        loadNotesPin()
    }

     fun loadNotesPined() {
        val dbNoteChild =
            dbNotes.child(auth.currentUser?.uid.toString())
                .child("noteList")
                .orderByChild("pin")
                .equalTo(true)
        dbNoteChild.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val notes = mutableListOf<Note>()
                    for (noteSnapshot in snapshot.children) {
                        val author = noteSnapshot.getValue(Note::class.java)
                        if (author != null) {
                            author.let { notes.add(it) }
                            noteList.postValue(notes)
                        }
                    }
                }
            }
        })
    }

     fun loadNotesPin() {
        val dbNoteChild = dbNotes.child(auth.currentUser?.uid.toString())
            .child("noteList")
            .orderByChild("pin")
            .equalTo(false)
        dbNoteChild.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val notes = mutableListOf<Note>()
                    for (noteSnapshot in snapshot.children) {
                        val author = noteSnapshot.getValue(Note::class.java)
                        if (author != null) {
                            author.let { notes.add(it) }
                            noteList.postValue(notes)
                        }
                    }

                }
            }
        })
    }
}