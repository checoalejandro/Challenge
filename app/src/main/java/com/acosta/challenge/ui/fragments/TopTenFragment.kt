package com.acosta.challenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.acosta.challenge.adapters.IndicesAdapter
import com.acosta.challenge.databinding.FragmentTopTenBinding
import com.acosta.challenge.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Will list top/bottom items from market
 *
 * @author @seacosta
 */
class TopTenFragment : Fragment() {

    private val viewModel: HomeViewModel by sharedViewModel()
    private lateinit var binding: FragmentTopTenBinding
    private lateinit var adapter: IndicesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopTenBinding.inflate(inflater)
        setUI()
        setObservers()
        return binding.root
    }

    private fun setUI() {
        adapter = IndicesAdapter()
        binding.rv.adapter = adapter
    }

    @MainThread
    private fun progressIndicator(show: Boolean) {
        binding.lineProgress.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getTopTen()
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                    .onEach { progressIndicator(true) }
                    .collect {
                        adapter.setItems(it)
                        progressIndicator(false)
                    }
            }
        }
    }
}