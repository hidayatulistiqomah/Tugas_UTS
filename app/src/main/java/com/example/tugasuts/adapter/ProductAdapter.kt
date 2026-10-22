package com.example.tugasuts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasuts.databinding.ItemDataBinding
import com.example.tugasuts.model.ProductItem

class ProductAdapter : ListAdapter<ProductItem,
        ProductAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ProductItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemDataBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(product)
        }
    }

    class MyViewHolder(val binding: ItemDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductItem) {
            binding.tvJudul.text = product.title ?: "-"
            binding.tvHarga.text = "Harga: USD ${product.price ?: 0.0}"
            binding.tvStok.text = "Stok: ${product.stock ?: 0}"
            binding.tvRating.text = "Rating: ${product.rating ?: 0.0}"

            // gambar dari kolom thumbnail (URL lengkap)
            Glide.with(itemView.context)
                .load(product.thumbnail)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.imgThumbnail)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductItem>() {
            override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
