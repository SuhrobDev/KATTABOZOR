package dev.soul.kattabozor.presentation.utils

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

sealed class LoadState {
    object Init : LoadState()
    object Loading : LoadState()
    object Loaded : LoadState()

    sealed class Error : LoadState()
    data class ToastError(val message: String, val cause: Throwable?) : Error()

}

suspend fun withLoadState(
    loadState: MutableLiveData<LoadState>,
    loader: suspend () -> Unit
) {
    if (loadState.value != LoadState.Loading) {
        loadState.value = LoadState.Loading
        loadState.value = try {
            loader()
            LoadState.Loaded
        } catch (e: Throwable) {
            e.toErrorState()
        }
    }
}

suspend fun <T> MutableLiveData<T>.updateWithLoadState(
    loadState: MutableLiveData<LoadState>,
    loader: suspend () -> T
) {
    if (loadState.value != LoadState.Loading) {
        loadState.value = LoadState.Loading
        loadState.value = try {
            value = loader()
            LoadState.Loaded
        } catch (e: Throwable) {
            e.toErrorState()
        }
    }
}

suspend fun <T> MutableLiveData<T>.updateFromResultWithLoadState(
    loadState: MutableLiveData<LoadState>,
    loader: suspend () -> Result<T>
) {
    if (loadState.value != LoadState.Loading) {
        loadState.value = LoadState.Loading

        val result = loader()
        if (result.isSuccess) {
            value = result.getOrNull()
            loadState.value = LoadState.Loaded
        } else {
            val e = result.exceptionOrNull()
            loadState.value = e?.toErrorState()
        }
    }
}

private fun Throwable?.toErrorState(): LoadState.Error {
    return LoadState.ToastError(message = "${this?.message}", this)
}


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