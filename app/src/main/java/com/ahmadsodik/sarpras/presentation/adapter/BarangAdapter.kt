package com.ahmadsodik.sarpras.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmadsodik.sarpras.data.source.model.Barang
import com.ahmadsodik.sarpras.databinding.CardLaboratoriumBinding
import com.bumptech.glide.Glide

class BarangAdapter(
    private val onItemClicked: (data: Barang) -> Unit
): ListAdapter<Barang, BarangAdapter.BarangViewHolder>(DIFF_CALLBACK) {

    inner class BarangViewHolder(private val binding: CardLaboratoriumBinding) :
    RecyclerView.ViewHolder(binding.root){
        fun bind(data: Barang) {
            binding.apply {
                tvBarang.text = data.nama
                tvJumlah.text = data.jumlah.toString()

                Glide.with(ivGambar.context)
                    .load(data.gambar)
                    .into(ivGambar)
            }
            itemView.setOnClickListener {
                onItemClicked(data)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val binding = CardLaboratoriumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BarangViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Barang>(){
            override fun areItemsTheSame(oldItem: Barang, newItem: Barang): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Barang, newItem: Barang): Boolean =
                oldItem == newItem


        }
    }
}