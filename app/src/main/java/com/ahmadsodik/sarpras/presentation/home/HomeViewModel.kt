package com.ahmadsodik.sarpras.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ahmadsodik.sarpras.data.repository.barang.BarangRepository
import com.ahmadsodik.sarpras.data.source.model.Barang
import com.ahmadsodik.sarpras.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val barangRepository: BarangRepository
): ViewModel() {
    fun getDataKelas() : LiveData<Result<List<Barang?>>> {
        return barangRepository.ambilDataBarangKelas()
    }
    fun getDataLaboratorium() : LiveData<Result<List<Barang?>>> {
        return barangRepository.ambilDataBarangLaboratorium()
    }
}