package com.henrynam.mynote.presentation.addnote

import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.henrynam.mynote.R
import com.henrynam.mynote.data.Constants.NODE_AUTHORS
import com.henrynam.mynote.data.Note
import com.henrynam.mynote.databinding.ActivityAddNodeBinding
import com.henrynam.mynote.presentation.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddNodeActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityAddNodeBinding
    private lateinit var auth: FirebaseAuth

    private val dbAuthors = FirebaseDatabase.getInstance().getReference(NODE_AUTHORS)

    private val viewModel: AddNodeViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AddNodeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_node)
        binding.viewModel = viewModel

        auth = FirebaseAuth.getInstance()

        if (intent.extras != null) {

           val data: Note = intent.getParcelableExtra("data")
            binding.edtTitle.setText(data.title.toString())
            binding.edtDescription.setText(data.description.toString())
        }

        binding.ivLeft.setOnClickListener {
            finish()
        }

        val df = SimpleDateFormat("HH:mm - dd/MM/YYYY")
        val formattedDate: String = df.format(Calendar.getInstance().time)
        binding.tvTime.text = "Đã chỉnh sửa  $formattedDate"

        binding.tvDone.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val description = binding.edtDescription.text.toString()

            if (TextUtils.isEmpty(title) && TextUtils.isEmpty(description)) {
                return@setOnClickListener
            }

            val note = Note()
            note.title = title
            note.description = description
            note.createdAt = formattedDate

            addNote(note)
        }

    }

    private fun addNote(note: Note) {
        dbAuthors.child(auth.currentUser?.uid.toString()).child(getString(R.string.note_list)).child(Calendar.getInstance().timeInMillis.toString()).setValue(note)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    finish()
                }
            }
    }


}
