package com.ahmadsodik.sarpras.presentation.sedang_dipinjam

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadsodik.sarpras.databinding.FragmentSedangDipinjamBinding
import com.ahmadsodik.sarpras.presentation.adapter.HistoryAdapter
import com.ahmadsodik.sarpras.presentation.detail_history.DetailHistoryActivity
import com.ahmadsodik.sarpras.util.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SedangDipinjamFragment : Fragment() {

    private val viewModel by viewModels<SedangDipinjamViewModel>()
    private var _binding: FragmentSedangDipinjamBinding? = null
    private val binding get() = _binding
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSedangDipinjamBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyAdapter = HistoryAdapter(
            onItemClick = {
                val intent = Intent(requireActivity(), DetailHistoryActivity::class.java).apply {
                    putExtra(DetailHistoryActivity.EXTRA_PINJAM, it)
                }
                startActivity(intent)
            }
        )
        binding?.rvHistory?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = historyAdapter
        }

        viewModel.ambilRiwayatSedangDipinjam().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {}
                is Result.Loading -> {}
                is Result.Success -> {
                    historyAdapter.submitList(result.data)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.ambilRiwayatSedangDipinjam().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {}
                is Result.Loading -> {}
                is Result.Success -> {
                    historyAdapter.submitList(result.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}