package com.henrynam.mynote.presentation.adapter

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun visible(view: View, value: Boolean) {
    if (value) view.visibility = View.VISIBLE else view.visibility = View.GONE
}