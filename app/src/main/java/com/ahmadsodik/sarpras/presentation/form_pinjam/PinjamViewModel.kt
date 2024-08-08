package com.ahmadsodik.sarpras.presentation.form_pinjam

import androidx.lifecycle.ViewModel
import com.ahmadsodik.sarpras.data.repository.pinjaman.PinjamRepository
import com.ahmadsodik.sarpras.data.source.model.Pinjam
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PinjamViewModel @Inject constructor(
    private val pinjamRepository: PinjamRepository
): ViewModel() {

    fun inputPinjaman(namaPeralatan: String, pinjam: Pinjam) = pinjamRepository.inputPinjaman(namaPeralatan, pinjam)
}