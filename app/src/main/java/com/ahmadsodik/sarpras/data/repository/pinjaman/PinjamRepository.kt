package com.ahmadsodik.sarpras.data.repository.pinjaman

import androidx.lifecycle.LiveData
import com.ahmadsodik.sarpras.data.source.model.Pinjam
import com.ahmadsodik.sarpras.util.Result

interface PinjamRepository {
    fun inputPinjaman(namaPeralatan: String, pinjam: Pinjam): LiveData<Result<String>>
    fun ambilRiwayatSedangDipinjam(): LiveData<Result<List<Pinjam?>>>
    fun ambilRiwayatSelesaiDipinjam(): LiveData<Result<List<Pinjam?>>>
    fun tandaiPinjamanSelesai(pinjam: Pinjam?): LiveData<Result<String?>>
}
