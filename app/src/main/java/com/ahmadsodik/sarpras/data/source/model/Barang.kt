package com.ahmadsodik.sarpras.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Barang(
    val id: String? = null,
    val nama: String? = null,
    val jumlah: Int? = 0,
    val gambar: String? = null,
    val namaPeralatan: String? = null,
): Parcelable