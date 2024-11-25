package com.ahmadsodik.sarpras.presentation.detail_history

import android.content.Intent
import android.net.Uri
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
import com.ahmadsodik.sarpras.util.Result
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailHistoryActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailHistoryBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<DetailHistoryViewModel>()


    private fun openWhatsApp(pinjam: Pinjam) {
        val message = """
        Halo ${pinjam.namaPeminjam},
        
        Detail Peminjaman:
        Nama Barang: ${pinjam.nama}
        Jumlah: ${pinjam.jumlahPinjamBarang}
        Tanggal Pinjam: ${pinjam.tanggalPinjam}
        Tanggal Kembali: ${pinjam.tanggalKembali}
        Keperluan: ${pinjam.keperluan}
        
        Mohon untuk mengembalikan tepat waktu sesuai tanggal yang telah ditentukan.
        
        Terima kasih.
    """.trimIndent()

        val intent = Intent(Intent.ACTION_VIEW)
        val encodedMessage = Uri.encode(message)
        val url = "https://api.whatsapp.com/send?phone=62${pinjam.nomorTelepon?.substring(1)}&text=$encodedMessage"
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

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
            tvNomorTelepon.text = pinjam?.nomorTelepon

            btnWhatsapp.setOnClickListener {
                pinjam?.let { data ->
                    openWhatsApp(data)
                }
            }

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