package com.ahmadsodik.sarpras.data.repository.pinjaman

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ahmadsodik.sarpras.data.source.firebase.FirebaseService
import com.ahmadsodik.sarpras.data.source.model.Pinjam
import com.ahmadsodik.sarpras.util.Result
import javax.inject.Inject

class PinjamRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService
) : PinjamRepository {

    override fun inputPinjaman(namaPeralatan: String, pinjam: Pinjam): LiveData<Result<String>> = liveData {
        emit(Result.Loading())
        try {
            firebaseService.inputPinjaman(namaPeralatan, pinjam)
            emit(Result.Success("Data Berhasil Di Input, Silahkan cek di riwayat"))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    override fun ambilRiwayatSedangDipinjam(): LiveData<Result<List<Pinjam?>>> = liveData {
        emit(Result.Loading())
        try {
            val result = firebaseService.ambilRiwayatSedangDipinjam()
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    override fun ambilRiwayatSelesaiDipinjam(): LiveData<Result<List<Pinjam?>>> = liveData {
        emit(Result.Loading())
        try {
            val result = firebaseService.ambilRiwayatSelesaiDipinjam()
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    override fun tandaiPinjamanSelesai(pinjam: Pinjam?): LiveData<Result<String?>> = liveData {
        emit(Result.Loading())
        try {
            firebaseService.tandaiPinjamanSelesai(pinjam)
            emit(Result.Success("Sukses Mengupdate"))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }
}
