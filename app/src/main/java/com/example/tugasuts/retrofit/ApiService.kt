package com.example.tugasuts.retrofit

import com.example.tugasuts.response.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    fun getProducts(
        @Query("limit") limit: Int,
        @Query("select") select: String
    ): Call<ProductResponse>
}
