package dev.soul.kattabozor.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import dev.soul.kattabozor.presentation.extensions.observeLoadState
import dev.soul.kattabozor.presentation.utils.LoadState
import dev.soul.kattabozor.presentation.utils.TouchBlockingFrameLayout

abstract class BaseFragment<Binding : ViewBinding> : Fragment() {

    protected abstract val logTag: String

    companion object {
        private const val TAG = "TouchBlockingFrameLayout"
    }

    protected lateinit var binding: Binding

    private var touchBlockingFrameLayout: TouchBlockingFrameLayout? = null
    protected var allowTouches: Boolean
        get() = touchBlockingFrameLayout?.blockTouches ?: true
        set(value) {
            touchBlockingFrameLayout?.blockTouches = !value
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (view != null) view
        else {
            binding = createBinding(inflater, container)
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        created(view, savedInstanceState)
    }

    abstract fun createBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    abstract fun created(view: View, savedInstanceState: Bundle?)

    fun showToast(str: String) {
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
    }

    protected fun LiveData<LoadState>.observeLoadState(
        progressView: View,
    ) {
        observeLoadState(
            fragment = this@BaseFragment,
            allowTouches = { allow -> allowTouches = allow },
            progressView = progressView,
            tag = logTag,
        )
    }
}