package uz.rounded.montekrist.presentation.common

data class UIObjectState<T>(
    val error: String = "",
    val data: T? = null,
    val isLoading: Boolean = false
)