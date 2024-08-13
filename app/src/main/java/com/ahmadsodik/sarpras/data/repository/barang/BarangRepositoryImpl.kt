package com.ahmadsodik.sarpras.data.repository.barang

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ahmadsodik.sarpras.data.source.firebase.FirebaseService
import com.ahmadsodik.sarpras.data.source.model.Barang
import com.ahmadsodik.sarpras.util.Result
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class BarangRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService
) : BarangRepository {

    override fun login(email: String, password: String): LiveData<Result<FirebaseUser?>> = liveData {
        emit(Result.Loading())
        try {
            val result = firebaseService.login(email, password)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    override fun getCurrentUser(): LiveData<FirebaseUser?> = liveData {
        val user = firebaseService.getCurrentUser()
        emit(user)
    }

    override suspend fun logout() {
        firebaseService.logout()
    }

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