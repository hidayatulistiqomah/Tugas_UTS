package com.example.tugasuts.ui.beranda

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasuts.R
import com.example.tugasuts.adapter.ProductAdapter
import com.example.tugasuts.databinding.FragmentBerandaBinding
import com.example.tugasuts.helpers.Config
import com.example.tugasuts.model.ProductItem
import com.example.tugasuts.response.ProductResponse
import com.example.tugasuts.retrofit.ApiConfig
import com.example.tugasuts.ui.DetailProductActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BerandaFragment : Fragment(R.layout.fragment_beranda) {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "BerandaFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBerandaBinding.bind(view)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvData.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvData.addItemDecoration(itemDecoration)

        getProductList()

        binding.swipeRefresh.setOnRefreshListener {
            getProductList()
        }
    }

    private fun getProductList() {
        showLoading(true)
        val client = ApiConfig.getApiService()
            .getProducts(Config.LIMIT, Config.SELECT_FIELDS)
        client.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (_binding == null) return
                showLoading(false)
                if (response.isSuccessful) {
                    response.body()?.products?.let { setProductData(it) }
                } else {
                    Log.e(TAG, "onResponse gagal: ${response.message()}")
                    Toast.makeText(requireContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                if (_binding == null) return
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
                Toast.makeText(requireContext(), "Tidak dapat terhubung ke server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setProductData(products: List<ProductItem>) {
        val adapter = ProductAdapter()
        adapter.submitList(products)
        binding.rvData.adapter = adapter
        adapter.setOnItemClickCallback(object : ProductAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ProductItem) {
                val json = Gson().toJson(data)
                val intent = Intent(requireContext(), DetailProductActivity::class.java)
                intent.putExtra(Config.EXTRA_PRODUCT, json)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        // ProgressBar tampil hanya jika loading DAN tidak sedang swipe refresh
        if (!binding.swipeRefresh.isRefreshing) {
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        // matikan indikator swipe saat loading selesai
        if (!isLoading) {
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
