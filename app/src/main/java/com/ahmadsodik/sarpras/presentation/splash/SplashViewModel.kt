package com.ahmadsodik.sarpras.presentation.splash

import androidx.lifecycle.ViewModel
import com.ahmadsodik.sarpras.data.repository.barang.BarangRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val barangRepository: BarangRepository
) : ViewModel() {

    fun getCurrentUser() = barangRepository.getCurrentUser()
}