package com.example.mistore.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mistore.model.CartProduct
import com.example.mistore.model.ProductsModel
import com.example.mistore.model.ProductsModelItem
import com.example.mistore.model.UserCartProduct

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProducts(products : ProductsModel)

    @Query("SELECT * FROM ProductsModelItem")
    suspend fun getProducts() : List<ProductsModelItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToCardProduct(cartProduct: CartProduct)

    @Query("SELECT * FROM CartProduct")
    suspend fun getCartProducts():List<CartProduct>

    @Query("SELECT COUNT(*) FROM CartProduct WHERE productId = :productId")
    suspend fun productExist(productId:Int):Int

    @Query("SELECT * FROM ProductsModelItem LEFT JOIN CartProduct ON ProductsModelItem.id = CartProduct.productId WHERE CartProduct.productId IS NOT NULL")
    suspend fun getUserProducts():List<UserCartProduct>


    // update product quantity
    @Query("UPDATE CartProduct SET quntity = :newQuantity WHERE productId = :productId")
    suspend fun updateProductQuantity(productId: Int, newQuantity: Int)


    @Delete
    suspend fun deleteCart(cartProduct: CartProduct)

}