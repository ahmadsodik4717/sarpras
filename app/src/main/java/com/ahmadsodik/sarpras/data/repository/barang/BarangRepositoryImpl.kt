package com.ahmadsodik.sarpras.data.repository.barang

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ahmadsodik.sarpras.data.source.firebase.FirebaseService
import com.ahmadsodik.sarpras.data.source.model.Barang
import com.ahmadsodik.sarpras.util.Result
import javax.inject.Inject

class BarangRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService
) : BarangRepository {
    override fun ambilDataBarangKelas() = liveData {
        emit(Result.Loading())
        try {
            val result = firebaseService.ambilDataKelas()
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    override fun ambilDataBarangLaboratorium(): LiveData<Result<List<Barang?>>> = liveData {
        emit(Result.Loading())
        try {
            val result = firebaseService.ambilDataLab()
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }
}