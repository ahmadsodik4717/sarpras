package com.ahmadsodik.sarpras.data.repository.barang

import androidx.lifecycle.LiveData
import com.ahmadsodik.sarpras.data.source.model.Barang
import com.ahmadsodik.sarpras.util.Result
import com.google.firebase.auth.FirebaseUser

interface BarangRepository {

    fun login(email: String, password: String): LiveData<Result<FirebaseUser?>>

    fun getCurrentUser(): LiveData<FirebaseUser?>

    suspend fun logout()

    fun ambilDataBarangKelas() : LiveData<Result<List<Barang?>>>

    fun ambilDataBarangLaboratorium() : LiveData<Result<List<Barang?>>>
}