package com.acosta.challenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.acosta.challenge.databinding.FragmentTopTenBinding
import com.acosta.challenge.ui.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Will list top/bottom items from market
 *
 * @author @seacosta
 */
class TopTenFragment : Fragment() {

    private val viewModel: HomeViewModel by sharedViewModel()
    private lateinit var binding: FragmentTopTenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopTenBinding.inflate(inflater)

        setObservers()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.topTenLiveData.observe(this) {
            if (it == null) return@observe
        }
    }

    private fun setObservers() {

    }
}