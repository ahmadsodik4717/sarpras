package com.ahmadsodik.sarpras.presentation.login

import androidx.lifecycle.ViewModel
import com.ahmadsodik.sarpras.data.repository.barang.BarangRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val barangRepository: BarangRepository
) : ViewModel() {

    fun login(email: String, password: String) = barangRepository.login(email, password)
}