package com.example.mistore.intefaces

import com.example.mistore.model.CartProduct

interface CartUpdateListener {

    fun update(id:Int,newValue:Int)
    fun getTotal(total:Double)
    fun onDeleteCartListener(cartProduct: CartProduct, pos:Int)

}