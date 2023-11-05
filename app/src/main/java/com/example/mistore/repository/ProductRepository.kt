package com.cheezycode.daggermvvm.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mistore.db.ProductDb
import com.example.mistore.model.CartProduct
import com.example.mistore.model.ProductsModel
import com.example.mistore.model.ProductsModelItem
import com.example.mistore.model.UserCartProduct
import com.example.mistore.networkin.FakeApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import java.io.IOException
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val fakerAPI: FakeApi,
    private val productDb: ProductDb,
    @ApplicationContext private val context: Context
) {

    private val _products = MutableLiveData<ProductsModel>()
    val products: LiveData<ProductsModel>
        get() = _products

    private  val _cartProduct = MutableLiveData<List<UserCartProduct>>()
    val cartProduct:LiveData<List<UserCartProduct>>
        get() = _cartProduct


    suspend fun getProducts() {

        var data: List<ProductsModelItem>? = productDb.getDAO().getProducts()

        if (data!!.isEmpty()) {
            val result = fakerAPI.getAllProducts()
            if (result.isSuccessful && result.body() != null) {
                Log.d("FDTS", "data come from api  ")
                _products.postValue(result.body())

                // store data in room database
                productDb.getDAO().addProducts(result.body()!!)

            }

        } else {
            _products.postValue(ProductsModel().apply { addAll(data) })
            Log.d("FDTS", "data come from room database  ")

        }

    }

   suspend fun addToCart(cartProduct: CartProduct){
      var count =  productDb.getDAO().productExist(cartProduct.productId)

       if(count > 0)
       {
           CoroutineScope(Dispatchers.Main).launch {
               // Call your suspend function here
               Toast.makeText(context, " product already added  ", Toast.LENGTH_SHORT).show()
           }
       }
       else
       {
           productDb.getDAO().addToCardProduct(cartProduct)
           CoroutineScope(Dispatchers.Main).launch {
               // Call your suspend function here
               Toast.makeText(context, " added Successfully  ", Toast.LENGTH_SHORT).show()
           }

       }
    }
    suspend fun getCartProducts(){
        var list = productDb.getDAO().getUserProducts()
        _cartProduct.postValue(list)
    }
    suspend fun uupdateQuantity(id:Int,newValue:Int){
        productDb.getDAO().updateProductQuantity(id,newValue)

    }
    suspend fun deleteCart(cartProduct: CartProduct){
        productDb.getDAO().deleteCart(cartProduct)


    }

}