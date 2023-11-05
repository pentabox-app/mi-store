package com.example.mistore.intefaces

interface CartUpdateListener {

    fun update(id:Int,newValue:Int)
    fun getTotal(total:Double)

}