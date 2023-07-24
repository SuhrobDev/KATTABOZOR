package dev.soul.kattabozor.presentation.ui.offers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.soul.kattabozor.domain.model.MainModel
import dev.soul.kattabozor.domain.usecase.offers.OffersUseCase
import dev.soul.kattabozor.domain.utils.Resource
import dev.soul.kattabozor.presentation.utils.LoadState
import dev.soul.kattabozor.presentation.utils.withLoadState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.rounded.montekrist.presentation.common.UIObjectState
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor(private val useCase: OffersUseCase) : ViewModel() {
    private val _uiLoadState = MutableLiveData<LoadState>()
    val uiLoadState: LiveData<LoadState> = _uiLoadState

    private val _offers = MutableStateFlow(UIObjectState<MainModel>())
    val offers = _offers.asStateFlow()

    fun getOffers() {
        viewModelScope.launch {
            withLoadState(_uiLoadState) {
                useCase.invoke().collect {
                    when (it) {
                        is Resource.Success -> _offers.value = UIObjectState(data = it.data)
                        is Resource.Loading -> _offers.value = UIObjectState(isLoading = true)
                        is Resource.Error -> _offers.value =
                            UIObjectState(it.message ?: "unknown error")
                    }
                }
            }
        }

    }

}