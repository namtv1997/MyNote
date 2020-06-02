package com.henrynam.mynote.presentation.addnote

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.henrynam.mynote.R
import com.henrynam.mynote.data.Constants.DATA
import com.henrynam.mynote.data.Note
import com.henrynam.mynote.databinding.ActivityAddNodeBinding
import com.henrynam.mynote.presentation.base.BaseActivity
import com.henrynam.mynote.presentation.main.MainActivity
import javax.inject.Inject


class AddNodeActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityAddNodeBinding

    private var note = Note()
    private var isEditNote: Boolean = false
    private var isClick: Boolean = false

    private val viewModel: AddNodeViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AddNodeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_node)
        binding.viewModel = viewModel

        if (intent.extras != null) {
            val data: Note = intent.getParcelableExtra(DATA)
            viewModel.noteData.set(data)
            note = data
            isEditNote = true
        } else {
            isEditNote = false
        }

        viewModel.success.observe(this, androidx.lifecycle.Observer {
            finish()
        })

        viewModel.startMainActivity.observe(this, androidx.lifecycle.Observer {
            startActivity(Intent(this, MainActivity::class.java))
        })

        binding.ivLeft.setOnClickListener {
            finish()
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

    private fun delete() {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.label_confirm_delete_note))
            .setNegativeButton(getString(R.string.label_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.label_accept)) { dialog, _ ->
                viewModel.deleteNote(note)
            }
            .show()
    }
}
