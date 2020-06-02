package com.henrynam.mynote.presentation.addnote

import android.annotation.SuppressLint
import android.content.Intent
import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.henrynam.mynote.R
import com.henrynam.mynote.data.Constants.NOTE_LIST
import com.henrynam.mynote.data.Note
import com.henrynam.mynote.presentation.main.MainActivity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@SuppressLint("SimpleDateFormat")
class AddNodeViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbNotes: DatabaseReference
) : ViewModel() {

    val success = MutableLiveData<Boolean>()
    val startMainActivity = MutableLiveData<Boolean>()
    val time = ObservableField<String>()
    val title = ObservableField<String>()
    val description = ObservableField<String>()
    val df = SimpleDateFormat("HH:mm - dd/MM/YYYY")
    val formattedDate: String = df.format(Calendar.getInstance().time)
    val noteData = ObservableField(Note())

    init {
        dbNotes.keepSynced(true)
        time.set("Đã chỉnh sửa $formattedDate").toString()
        title.set(noteData.get()?.title).toString()
        description.set(noteData.get()?.description).toString()
    }

  private fun addNote(note: Note) {
        dbNotes.child(auth.currentUser?.uid.toString()).child(NOTE_LIST)
            .child(note.key.toString()).setValue(note)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    success.postValue(true)
                }
            }
        success.postValue(true)
    }

    fun deleteNote(note: Note) {
        dbNotes.child(auth.currentUser?.uid.toString()).child(NOTE_LIST)
            .child(note.key.toString()).setValue(null)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    startMainActivity.postValue(true)
                }
            }
        success.postValue(true)
    }

    fun clickButtonDone() {
        noteData.get()?.let {
            if (it.key  == null) it.key = dbNotes.push().key
            it.createdAt = formattedDate
            addNote(it)
        }
    }

}