package com.ahmadsodik.sarpras.presentation.detail_history

import androidx.lifecycle.ViewModel
import com.ahmadsodik.sarpras.data.repository.pinjaman.PinjamRepository
import com.ahmadsodik.sarpras.data.source.model.Pinjam
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailHistoryViewModel @Inject constructor(
    private val pinjamRepository: PinjamRepository
) : ViewModel() {

    fun tandaiPinjamanSelesai(pinjam: Pinjam?) = pinjamRepository.tandaiPinjamanSelesai(pinjam)
}