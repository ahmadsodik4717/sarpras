package com.ahmadsodik.sarpras.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmadsodik.sarpras.data.repository.barang.BarangRepository
import com.ahmadsodik.sarpras.data.source.model.Barang
import com.ahmadsodik.sarpras.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val barangRepository: BarangRepository
): ViewModel() {
    val getDataKelas : LiveData<Result<List<Barang?>>> = barangRepository.ambilDataBarangKelas()

    val getDataLaboratorium : LiveData<Result<List<Barang?>>> = barangRepository.ambilDataBarangLaboratorium()

    fun logout() = viewModelScope.launch {
        barangRepository.logout()
    }
}