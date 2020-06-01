package com.henrynam.mynote.presentation.adapter

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.henrynam.mynote.R
import com.henrynam.mynote.data.Note

@BindingAdapter("visible")
fun visible(view: View, value: Boolean) {
    if (value) view.visibility = View.VISIBLE else view.visibility = View.GONE
}

@BindingAdapter("changeImagePin")
fun changeImagePin(image: AppCompatImageView, note: Note?) {
    if (note != null) {
        if (note.isPin) image.setImageResource(R.drawable.ic_pined)
        else image.setImageResource(R.drawable.ic_pin)
    }
}
