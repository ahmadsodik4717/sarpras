package com.ahmadsodik.sarpras.presentation.detail_history

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.ahmadsodik.sarpras.R
import com.ahmadsodik.sarpras.data.source.model.Pinjam
import com.ahmadsodik.sarpras.databinding.ActivityDetailHistoryBinding
import com.ahmadsodik.sarpras.presentation.MainActivity
import com.ahmadsodik.sarpras.util.Result
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailHistoryActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailHistoryBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<DetailHistoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pinjam = IntentCompat.getParcelableExtra(intent, EXTRA_PINJAM, Pinjam::class.java)

        binding.apply {
            Glide.with(this@DetailHistoryActivity)
                .load(pinjam?.gambar)
                .into(ivGambar)
            tvJumlah.text = pinjam?.jumlahPinjamBarang.toString()
            tvKeperluan.text = pinjam?.keperluan
            tvNamaBarang.text = pinjam?.nama
            tvNamaPeminjam.text = pinjam?.namaPeminjam
            tvTanggalPinjam.text = pinjam?.tanggalPinjam
            tvTanggalKembali.text = pinjam?.tanggalKembali

            btnSelesai.isVisible = pinjam?.status == "sedang dipinjam"
            btnSelesai.setOnClickListener {
                viewModel.tandaiPinjamanSelesai(pinjam).observe(this@DetailHistoryActivity) { result ->
                    when (result) {
                        is Result.Error -> Toast.makeText(
                            this@DetailHistoryActivity,
                            result.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        is Result.Loading -> {}
                        is Result.Success -> {
                            Toast.makeText(this@DetailHistoryActivity, result.data, Toast.LENGTH_SHORT)
                                .show()
                            moveToHome()
                        }
                    }
                }
            }
        }
    }

    private fun moveToHome() {
        onBackPressedDispatcher.onBackPressed()
    }

    companion object {
        const val EXTRA_PINJAM = "pinjam"
    }
}