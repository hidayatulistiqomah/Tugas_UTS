package com.example.tugasuts.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tugasuts.R
import com.example.tugasuts.databinding.ActivityDetailProductBinding
import com.example.tugasuts.helpers.Config
import com.example.tugasuts.model.ProductItem
import com.example.tugasuts.model.Review
import com.google.gson.Gson

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.judul_detail)

        val json = intent.getStringExtra(Config.EXTRA_PRODUCT)
        val p = Gson().fromJson(json, ProductItem::class.java)

        // gambar detail dari kolom images (ambil yang pertama)
        Glide.with(applicationContext)
            .load(p.images?.firstOrNull())
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(binding.imgProduct)

        binding.tvTitle.text = p.title
        binding.tvPrice.text = "USD ${p.price}"
        binding.tvCategory.text = "Kategori: ${p.category}"
        binding.tvStock.text = "Stok: ${p.stock}"
        binding.tvStatus.text = "Status: ${p.availabilityStatus}"
        binding.tvRating.text = "Rating: ${p.rating}"
        binding.tvTags.text = "Tags: ${p.tags?.joinToString(", ") ?: "-"}"
        val d = p.dimensions
        binding.tvDimensions.text =
            "Dimensi (lebar x tinggi x dalam): ${d?.width} x ${d?.height} x ${d?.depth}"
        binding.tvDescription.text = p.description

        // review: tampilkan 1 teratas, link untuk melihat semuanya
        val reviews = p.reviews.orEmpty()
        val first = reviews.firstOrNull()
        binding.tvReviewTop.text = if (first != null) formatReview(first) else "Belum ada review"
        if (reviews.size > 1) {
            binding.tvLihatReview.visibility = View.VISIBLE
            binding.tvLihatReview.text = "Lihat semua review (${reviews.size})"
            binding.tvLihatReview.setOnClickListener { showAllReviews(reviews) }
        }
    }

    private fun formatReview(r: Review): String {
        return "${r.reviewerName} (${r.rating}/5)\n${r.comment}\n${r.date}"
    }

    private fun showAllReviews(reviews: List<Review>) {
        val semua = reviews.joinToString("\n\n") { formatReview(it) }
        AlertDialog.Builder(this)
            .setTitle("Semua Review")
            .setMessage(semua)
            .setPositiveButton("Tutup", null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
