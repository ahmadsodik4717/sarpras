package com.ahmadsodik.sarpras.presentation.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmadsodik.sarpras.R
import com.ahmadsodik.sarpras.databinding.FragmentHistoryBinding
import com.ahmadsodik.sarpras.presentation.adapter.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        binding?.viewPager?.adapter = sectionPagerAdapter

        TabLayoutMediator(binding!!.tabs, binding!!.viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAB_TITLES = arrayOf("Sedang Dipinjam", "Selesai Dipinjam")
    }
}