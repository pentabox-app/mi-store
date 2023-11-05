package com.example.mistore.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mistore.model.CartProduct
import com.example.mistore.model.ProductsModelItem


@Database(entities = [ProductsModelItem::class ,  CartProduct::class], version = 2)
abstract class ProductDb :RoomDatabase(){

    abstract fun getDAO() : ProductDAO


}