package com.example.mistore.networkin

import com.example.mistore.model.ProductsModel
import retrofit2.Response
import retrofit2.http.GET

interface FakeApi {


    @GET("products")
    suspend fun getAllProducts():Response<ProductsModel>

}