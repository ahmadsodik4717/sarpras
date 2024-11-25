package com.ahmadsodik.sarpras.presentation.form_pinjam

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ahmadsodik.sarpras.R
import com.ahmadsodik.sarpras.data.source.model.Barang
import com.ahmadsodik.sarpras.data.source.model.Pinjam
import com.ahmadsodik.sarpras.databinding.ActivityFormPinjamBinding
import com.ahmadsodik.sarpras.presentation.MainActivity
import com.ahmadsodik.sarpras.util.Result
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class FormPinjamActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {

    private lateinit var binding: ActivityFormPinjamBinding
    private val viewModel: PinjamViewModel by viewModels()

    private fun isValidPhoneNumber(phone: String): Boolean {
        return phone.length >= 10 && phone.length <= 13 && phone.all { it.isDigit() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFormPinjamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val pinjam = IntentCompat.getParcelableExtra(intent, EXTRA_PINJAM, Barang::class.java)

        binding.apply {
            tvNamaBarang.text = pinjam?.nama
            tvJumlahBarang.text = pinjam?.jumlah?.toString()

            Glide.with(this@FormPinjamActivity)
                .load(pinjam?.gambar)
                .into(ivGambar)

            btnTanggalPinjam.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(supportFragmentManager, DATE_PICKER_PINJAM)
            }

            btnTanggalKembali.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(supportFragmentManager, DATE_PICKER_KEMBALI)
            }

            btnInput.setOnClickListener {
                val phoneNumber = edtNomorTelepon.text.toString()

                if (edtNama.text.isNullOrBlank() ||
                    edtKeperluan.text.isNullOrBlank() ||
                    edtJumlah.text.isNullOrBlank()) {
                    Snackbar.make(root, "Form wajib diisi", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (!isValidPhoneNumber(phoneNumber)) {
                    Snackbar.make(root, "Nomor telepon harus 10-13 digit", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val namaBarang = pinjam?.nama
                val jumlahBarang = pinjam?.jumlah
                val gambar = pinjam?.gambar
                val namaPeminjam = binding.edtNama.text.toString()
                val jumlahPinjaman = binding.edtJumlah.text.toString().toInt()
                val tanggalPinjam = binding.tvTanggalPinjam.text.toString()
                val tanggalKembali = binding.tvTanggalKembali.text.toString()
                val keperluan = binding.edtKeperluan.text.toString()

                val dataPinjam = Pinjam(
                    id = pinjam?.id,
                    nama = namaBarang,
                    jumlah = jumlahBarang?.minus(jumlahPinjaman),
                    gambar = gambar,
                    namaPeminjam = namaPeminjam,
                    jumlahPinjamBarang = jumlahPinjaman,
                    tanggalPinjam = tanggalPinjam,
                    tanggalKembali = tanggalKembali,
                    keperluan = keperluan,
                    nomorTelepon = phoneNumber,
                    namaPeralatan = pinjam?.namaPeralatan.toString()
                )

                viewModel.inputPinjaman(pinjam?.namaPeralatan.toString(), dataPinjam)
                    .observe(this@FormPinjamActivity) { result ->
                        when(result) {
                            is Result.Error -> {
                                Toast.makeText(this@FormPinjamActivity, result.message, Toast.LENGTH_SHORT).show()
                            }
                            is Result.Loading -> {}
                            is Result.Success -> {
                                Toast.makeText(this@FormPinjamActivity,
                                    "Peminjaman berhasil! Kami akan menghubungi Anda di $phoneNumber",
                                    Toast.LENGTH_LONG).show()
                                moveToHome()
                            }
                        }
                    }
            }
        }
    }

    override fun onDialogDataSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

        when(tag) {
            DATE_PICKER_PINJAM -> {
                binding.tvTanggalPinjam.text = dateFormat.format(calendar.time)
            }
            DATE_PICKER_KEMBALI -> {
                binding.tvTanggalKembali.text = dateFormat.format(calendar.time)
            }
        }
    }

    private fun moveToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val EXTRA_PINJAM = "pinjam"
        private const val DATE_PICKER_PINJAM = "tanggal_pinjam"
        private const val DATE_PICKER_KEMBALI = "tanggal_kembali"
    }
}
