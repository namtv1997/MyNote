package com.henrynam.mynote.presentation.addnote

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.henrynam.mynote.R
import com.henrynam.mynote.data.Constants.NODE_AUTHORS
import com.henrynam.mynote.data.Note
import com.henrynam.mynote.databinding.ActivityAddNodeBinding
import com.henrynam.mynote.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.item_node.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class AddNodeActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityAddNodeBinding
    private lateinit var auth: FirebaseAuth
    private var isEditNote: Boolean = false
    private var note = Note()

    private var isClick: Boolean = false

    private val dbAuthors = FirebaseDatabase.getInstance().getReference(NODE_AUTHORS)

    private val viewModel: AddNodeViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AddNodeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_node)
        binding.viewModel = viewModel
        dbAuthors.keepSynced(true)
        auth = FirebaseAuth.getInstance()

        if (intent.extras != null) {
            val data: Note = intent.getParcelableExtra("data")
            note = data
            binding.edtTitle.setText(data.title.toString())
            binding.edtDescription.setText(data.description.toString())
            binding.tvTitleToolbar.text = "Edit Note"
            binding.tvTime.visibility = View.VISIBLE
            binding.ivLeft.setImageResource(R.drawable.ic_delete)
            isEditNote = true
            if (data.isPin) binding.ivPin.setImageResource(R.drawable.ic_pined)
            else binding.ivPin.setImageResource(R.drawable.ic_pin)

        } else {
            binding.tvTitleToolbar.text = "Add Note"
            binding.ivLeft.setImageResource(R.drawable.ic_cancel)
            binding.tvTime.visibility = View.GONE
            isEditNote = false
        }

        binding.ivLeft.setOnClickListener {
            finish()
        }

        val df = SimpleDateFormat("HH:mm - dd/MM/YYYY")
        val formattedDate: String = df.format(Calendar.getInstance().time)
        binding.tvTime.text = "Đã chỉnh sửa  $formattedDate"

        binding.ivDone.setOnClickListener {
            if (!isEditNote) {
                val title = binding.edtTitle.text.toString()
                val description = binding.edtDescription.text.toString()

                if (TextUtils.isEmpty(title) && TextUtils.isEmpty(description)) {
                    return@setOnClickListener
                }

                note.title = title
                note.key = dbAuthors.push().key
                note.description = description
                note.createdAt = formattedDate

                addNote(note)
            } else {
                note.title = binding.edtTitle.text.toString()
                note.description = binding.edtDescription.text.toString()
                note.createdAt = formattedDate

                updateNote(note)
            }
        }

        binding.ivPin.setOnClickListener {
            if (isClick) {
                binding.ivPin.setImageResource(R.drawable.ic_pined)
                note.isPin = true
            } else {
                binding.ivPin.setImageResource(R.drawable.ic_pin)
                note.isPin = false
            }

            isClick = !isClick
        }

        binding.ivLeft.setOnClickListener {
            if (!isEditNote) {
                finish()
            } else {
                delete()
            }
        }

    }

    private fun addNote(note: Note) {
        dbAuthors.child(auth.currentUser?.uid.toString()).child(getString(R.string.note_list))
            .child(note.key.toString()).setValue(note)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    finish()
                }
            }
        finish()
    }

    private fun updateNote(note: Note) {
        dbAuthors.child(auth.currentUser?.uid.toString()).child(getString(R.string.note_list))
            .child(note.key.toString()).setValue(note)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    finish()
                }
            }
        finish()
    }

    private fun deleteNote(note: Note) {
        dbAuthors.child(auth.currentUser?.uid.toString()).child(getString(R.string.note_list))
            .child(note.key.toString()).setValue(null)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent()
                    setResult(2, intent)
                    finish()
                }
            }
        finish()
    }

    private fun delete() {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.label_confirm_delete_note))
            .setNegativeButton(getString(R.string.label_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.label_accept)) { dialog, _ ->
                deleteNote(note)
            }
            .show()
    }
}
