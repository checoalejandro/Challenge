package com.acosta.challenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.acosta.challenge.databinding.FragmentTopTenBinding

/**
 * Will list top/bottom items from market
 *
 * @author @seacosta
 */
class TopTenFragment : Fragment() {

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
        return binding.root
    }
}