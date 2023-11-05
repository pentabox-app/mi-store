package com.example.mistore.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartProduct (
    val productId: Int,
    val  userId:Int = 1,
    @PrimaryKey(autoGenerate = true)
    val cartId:Int,
    var quntity:Int = 1
)
