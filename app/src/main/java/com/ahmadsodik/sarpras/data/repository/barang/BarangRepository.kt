package com.ahmadsodik.sarpras.data.repository.barang

import androidx.lifecycle.LiveData
import com.ahmadsodik.sarpras.data.source.model.Barang
import com.ahmadsodik.sarpras.util.Result

interface BarangRepository {

    fun ambilDataBarangKelas() : LiveData<Result<List<Barang?>>>

    fun ambilDataBarangLaboratorium() : LiveData<Result<List<Barang?>>>
}