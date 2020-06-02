package com.henrynam.mynote.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.henrynam.mynote.data.Constants.NOTE_LIST
import com.henrynam.mynote.data.Constants.PIN
import com.henrynam.mynote.data.Constants.TITLE
import com.henrynam.mynote.data.Note
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbNotes: DatabaseReference
) : ViewModel() {

    val noteList = MutableLiveData<List<Note>>()
    val haveData = MutableLiveData<Boolean>()
    init {
        dbNotes.keepSynced(true)
        loadNotes()
    }

    private fun loadNotes() {
        val dbNoteChild = dbNotes.child(auth.currentUser?.uid.toString())
            .child(NOTE_LIST)
            .orderByChild(PIN)
        dbNoteChild.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val notes = mutableListOf<Note>()
                    for (noteSnapshot in snapshot.children) {
                        val author = noteSnapshot.getValue(Note::class.java)
                        if (author != null) {
                            author.let { notes.add(it) }
                            val sortedList =
                                notes.sortedWith(compareBy({ !it.isPin }, { it.isPin }))

                            noteList.postValue(sortedList)
                        }
                    }

                }
            }
        })
    }

    fun searchNotes(searchText: String) {
        val dbNoteChild = dbNotes.child(auth.currentUser?.uid.toString())
            .child(NOTE_LIST).orderByChild(TITLE).startAt(searchText).endAt(searchText + "\uf8ff")
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

                }else{
                    haveData.postValue(false)
                }
            }
        })
    }
}