package com.ahmadsodik.sarpras.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pinjam(
    val id: String? = null,
    val documentId: String? = null,
    val nama: String? = null,
    val jumlah: Int? = 0,
    val gambar: String? = null,
    val namaPeminjam: String? = null,
    val jumlahPinjamBarang: Int = 0,
    val tanggalPinjam: String? = null,
    val tanggalKembali: String? = null,
    val keperluan: String? = null,
    val nomorTelepon: String? = null,
    val status: String = "sedang dipinjam",
    val namaPeralatan: String = "",
) : Parcelable