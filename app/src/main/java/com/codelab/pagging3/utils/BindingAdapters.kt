package com.codelab.pagging3.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.TextViewBindingAdapter.setDrawableLeft
import com.bumptech.glide.Glide
import com.codelab.pagging3.R
import com.codelab.pagging3.data.model.Status
import com.google.android.material.textview.MaterialTextView



@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    if (url.isNullOrEmpty()) return
    Glide.with(this).load(url).into(this)
}

@BindingAdapter("status")
fun MaterialTextView.status(status: Status) {
    text = status.toString()
    when (status) {
        Status.ALIVE -> setDrawableLeft(R.color.green_a700)
        Status.DEAD -> setDrawableLeft(R.color.red_a700)
        Status.UNKNOWN -> setDrawableLeft(R.color.gray_700)
    }
}