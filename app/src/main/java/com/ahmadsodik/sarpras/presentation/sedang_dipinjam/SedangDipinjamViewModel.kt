package com.ahmadsodik.sarpras.presentation.sedang_dipinjam

import androidx.lifecycle.ViewModel
import com.ahmadsodik.sarpras.data.repository.pinjaman.PinjamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SedangDipinjamViewModel @Inject constructor(
    private val pinjamRepository: PinjamRepository
) : ViewModel() {

    fun ambilRiwayatSedangDipinjam() = pinjamRepository.ambilRiwayatSedangDipinjam()
}