package dev.soul.kattabozor.presentation.ui.offers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.soul.kattabozor.databinding.FragmentOffersBinding
import dev.soul.kattabozor.presentation.base.BaseFragment
import dev.soul.kattabozor.presentation.ui.offers.adapter.OfferAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OffersFragment : BaseFragment<FragmentOffersBinding>() {

    override val logTag: String
        get() = "Offers_Fragment"

    private val viewModel: OffersViewModel by viewModels()

    private val adapter by lazy {
        OfferAdapter()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOffersBinding = FragmentOffersBinding.inflate(inflater)

    override fun created(view: View, savedInstanceState: Bundle?) {
        viewModel.uiLoadState.observeLoadState(binding.progress)
        binding.offerRv.adapter = adapter
        viewModel.getOffers()
        observeOffers()
    }

    private fun observeOffers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.offers.collect {
                it.data?.let { offers ->
                    adapter.submitList(offers.offers)

                    Log.d("Offers_Fragment", "observeOffers: $offers")
                }
                if (it.error.isNotBlank()) {
                    Toast.makeText(requireContext(), "Error: ${it.error}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }
}