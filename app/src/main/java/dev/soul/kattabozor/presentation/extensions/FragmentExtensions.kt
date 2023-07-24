package dev.soul.kattabozor.presentation.extensions

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import dev.soul.kattabozor.presentation.utils.LoadState
import dev.soul.kattabozor.presentation.utils.observeOnStarted

fun LiveData<LoadState>.observeLoadState(
    fragment: Fragment,
    allowTouches: (allow: Boolean) -> Unit,
    progressView: View,
    tag: String,
) {
    observeOnStarted(fragment.viewLifecycleOwner) {
        when (it) {
            is LoadState.Init -> {
                allowTouches(false)
                progressView.visibility = View.VISIBLE
            }
            is LoadState.Loading -> {
                allowTouches(false)
                progressView.visibility = View.VISIBLE
            }
            is LoadState.Loaded -> {
                allowTouches(true)
                progressView.visibility = View.GONE
            }
            is LoadState.ToastError -> {
                Log.e(tag, "Toast error: ${it.message}", it.cause)
                allowTouches(true)
                progressView.visibility = View.GONE
                Toast.makeText(fragment.requireContext(), it.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}