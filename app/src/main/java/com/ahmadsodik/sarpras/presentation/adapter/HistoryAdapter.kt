package com.ahmadsodik.sarpras.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmadsodik.sarpras.data.source.model.Pinjam
import com.ahmadsodik.sarpras.databinding.ItemHistoryBinding
import com.bumptech.glide.Glide

class HistoryAdapter(
    private val onItemClick: (Pinjam) -> Unit,
) : ListAdapter<Pinjam, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Pinjam) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.gambar)
                    .into(ivHistory)

                tvKeperluan.text = "Keperluan: ${item.keperluan}"
                tvNamaBarang.text = item.nama
                tvJumlahBarang.text = "Jumlah: ${item.jumlahPinjamBarang}"
                // Added phone number display

                itemView.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pinjam>() {
            override fun areItemsTheSame(oldItem: Pinjam, newItem: Pinjam): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Pinjam, newItem: Pinjam): Boolean {
                return oldItem == newItem
            }
        }
    }
}
