package com.ahmadsodik.sarpras.presentation.selesai

import androidx.lifecycle.ViewModel
import com.ahmadsodik.sarpras.data.repository.pinjaman.PinjamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelesaiViewModel @Inject constructor(
    private val pinjamRepository: PinjamRepository
) : ViewModel() {

    fun ambilRiwayatSelesaiDipinjam() = pinjamRepository.ambilRiwayatSelesaiDipinjam()
}