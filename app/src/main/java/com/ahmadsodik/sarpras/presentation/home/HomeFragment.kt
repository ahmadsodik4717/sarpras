package com.ahmadsodik.sarpras.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadsodik.sarpras.R
import com.ahmadsodik.sarpras.data.source.model.Barang
import com.ahmadsodik.sarpras.databinding.FragmentHomeBinding
import com.ahmadsodik.sarpras.presentation.adapter.BarangAdapter
import com.ahmadsodik.sarpras.presentation.form_pinjam.FormPinjamActivity
import com.ahmadsodik.sarpras.presentation.login.LoginActivity
import com.ahmadsodik.sarpras.util.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBarangKelas()
        setUpBarangLaboratorium()

        binding?.header?.btnLogout?.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireActivity())
                .setTitle("Logout Aplikasi")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Ya") { _, _ ->
                    viewModel.logout()
                    moveToLogin()
                }
                .create()

            alertDialog.show()
        }
    }

    private fun moveToLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun setUpBarangKelas() {
        val barangAdapter = BarangAdapter { barang ->
            val intent = Intent(requireActivity(), FormPinjamActivity::class.java).apply {
                putExtra(FormPinjamActivity.EXTRA_PINJAM, barang)
            }
            startActivity(intent)
        }

        binding?.contentHome?.rvKelas?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = barangAdapter
        }
        viewModel.getDataKelas.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    result.message
                }

                is Result.Loading -> {

                }

                is Result.Success -> {
                    barangAdapter.submitList(result.data)
                }
            }
        }

    }
    private fun setUpBarangLaboratorium() {
        val barangAdapter = BarangAdapter { barang ->
            val intent = Intent(requireActivity(), FormPinjamActivity::class.java).apply {
                putExtra(FormPinjamActivity.EXTRA_PINJAM, barang)
            }
            startActivity(intent)
        }

        binding?.contentHome?.rvLaboratorium?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = barangAdapter
        }
        viewModel.getDataLaboratorium.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    result.message
                }

                is Result.Loading -> {
                }

                is Result.Success -> {
                    barangAdapter.submitList(result.data)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}