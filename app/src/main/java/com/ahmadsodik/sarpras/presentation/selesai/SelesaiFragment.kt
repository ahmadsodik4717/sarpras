package com.ahmadsodik.sarpras.presentation.selesai

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadsodik.sarpras.databinding.FragmentSelesaiBinding
import com.ahmadsodik.sarpras.presentation.adapter.HistoryAdapter
import com.ahmadsodik.sarpras.presentation.detail_history.DetailHistoryActivity
import com.ahmadsodik.sarpras.util.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelesaiFragment : Fragment() {

    private val viewModel by viewModels<SelesaiViewModel>()
    private var _binding: FragmentSelesaiBinding? = null
    private val binding get() = _binding

    private val historyAdapter by lazy {
        HistoryAdapter { item ->
            startActivity(Intent(requireActivity(), DetailHistoryActivity::class.java).apply {
                putExtra(DetailHistoryActivity.EXTRA_PINJAM, item)
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelesaiBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()
    }

    private fun setupRecyclerView() {
        binding?.rvHistory?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = historyAdapter
        }
    }

    private fun observeData() {
        viewModel.ambilRiwayatSelesaiDipinjam().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> historyAdapter.submitList(result.data)
                is Result.Error -> {
                    // Handle error state
                }
                is Result.Loading -> {
                    // Handle loading state
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.ambilRiwayatSelesaiDipinjam()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
