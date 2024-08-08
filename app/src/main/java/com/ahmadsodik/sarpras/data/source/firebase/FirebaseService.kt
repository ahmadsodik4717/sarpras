package com.ahmadsodik.sarpras.data.source.firebase

import com.ahmadsodik.sarpras.data.source.model.Barang
import com.ahmadsodik.sarpras.data.source.model.Pinjam

interface FirebaseService {

    suspend fun ambilDataKelas(): List<Barang?>

    suspend fun ambilDataLab() : List<Barang?>

    suspend fun inputPinjaman(namaPeralatan: String, data: Pinjam)

    suspend fun ambilRiwayatSedangDipinjam(): List<Pinjam?>

    suspend fun ambilRiwayatSelesaiDipinjam(): List<Pinjam?>

    suspend fun tandaiPinjamanSelesai(pinjam: Pinjam?)
}