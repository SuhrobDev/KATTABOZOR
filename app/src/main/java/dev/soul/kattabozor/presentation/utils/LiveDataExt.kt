package dev.soul.kattabozor.presentation.utils

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> LiveData<T>.asDistinctFlow(): Flow<T> = distinctUntilChanged().asFlow()

fun <T> MutableLiveData<T>.updateValueDistinct(value: T) {
    if (this.value != value) {
        this.value = value
    }
}

fun <T> LiveData<T>.observeOnStarted(lifecycleOwner: LifecycleOwner, block: (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            asFlow().collect {
                block(it)
            }
        }
    }
}