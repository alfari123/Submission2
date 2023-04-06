package com.dicoding.core.utils

import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dicoding.core.BuildConfig
import com.dicoding.core.BuildConfig.URLS_IMG_ORIGINAL
import com.dicoding.core.BuildConfig.URLS_IMG
import com.dicoding.core.R

fun ImageView.setImageFromUrl(context: Context, url: String) {
    Glide
        .with(context)
        .load(url)
        .placeholder(R.drawable.ic_no_image)
        .error(R.drawable.ic_no_image)
        .into(this)
}

fun getImageOriginalUrl(path: String?): String = "${BuildConfig.URLS_IMG_ORIGINAL+path}"

fun getImageUrl(path: String?): String = "$URLS_IMG$path"

fun View.animateAlpha(isVisible: Boolean, duration: Long = 400) {
    ObjectAnimator
        .ofFloat(this, View.ALPHA, if (isVisible) 1f else 0f)
        .setDuration(duration)
        .start()
}
