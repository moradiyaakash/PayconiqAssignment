package com.payconiq.assignment.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

object BindingAdapters {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(view: ImageView, url: String?) {
        if (url.toString().isNotEmpty()) {
            Glide.with(view.context)
                .load(url.toString())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
    }
}