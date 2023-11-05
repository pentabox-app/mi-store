package com.cheezycode.daggermvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheezycode.daggermvvm.repository.ProductRepository
import com.example.mistore.model.CartProduct
import com.example.mistore.model.ProductsModel
import com.example.mistore.model.UserCartProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    val productsLiveData : LiveData<ProductsModel>
    get() = repository.products

    val cartLiveData:LiveData<List<UserCartProduct>>
        get() = repository.cartProduct


    init {
        viewModelScope.launch {

            repository.getProducts()
            repository.getCartProducts()

        }
    }
    fun addToCart(cartProduct: CartProduct){
        viewModelScope.launch{

            repository.addToCart(cartProduct)
        }
    }
    fun updateCartProductQuantity(id:Int, newValue:Int){

        viewModelScope.launch {
            repository.uupdateQuantity(id,newValue)
        }
    }
    fun deleteCart(cartProduct: CartProduct){
        viewModelScope.launch {
            repository.deleteCart(cartProduct)
        }

    }
}