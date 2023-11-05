package com.example.mistore.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserCartProduct(
    @Embedded
    var productsModelItem: ProductsModelItem?,

    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    var cartProduct: CartProduct?
)