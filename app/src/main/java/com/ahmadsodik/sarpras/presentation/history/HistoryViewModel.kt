package com.ahmadsodik.sarpras.presentation.history

import androidx.lifecycle.ViewModel
import com.ahmadsodik.sarpras.data.repository.barang.BarangRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val barangRepository: BarangRepository
): ViewModel() {
}