package dev.soul.kattabozor.presentation.extensions

import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

fun ImageView.loadImage(context: Context, path: String) {
    Log.d("dkkfjshdjn", "loadImage: $path")
    Glide.with(context).load(path).into(this)
}