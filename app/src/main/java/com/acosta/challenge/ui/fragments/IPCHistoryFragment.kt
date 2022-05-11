package com.acosta.challenge.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.acosta.challenge.charts.ChartFactory
import com.acosta.challenge.charts.parser.LineParser
import com.acosta.challenge.databinding.FragmentIPCHistoryBinding
import com.acosta.challenge.ui.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [IPCHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IPCHistoryFragment : Fragment() {

    private val viewModel: HomeViewModel by sharedViewModel()
    private lateinit var binding: FragmentIPCHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIPCHistoryBinding.inflate(inflater, container, false)
        setObservers()
        viewModel.getIPCHistory()
        return binding.root
    }

    private fun setObservers() {
        viewModel.ipcLiveData.observe(viewLifecycleOwner) { response ->
            if (response == null) {
                Log.d(
                    "IPCHistoryFragment",
                    "setObservers: No response from server received to IPC history"
                )
                return@observe
            }

            // Show data, if we need another type, ChartFactory.BAR can be used.
            ChartFactory.LINE.parse(response, LineParser(binding.lineChart))
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IPCHistoryFragment.
         */
        @JvmStatic
        fun newInstance() =
            IPCHistoryFragment()
    }
}