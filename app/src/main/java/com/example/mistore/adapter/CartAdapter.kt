package com.example.mistore.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mistore.R
import com.example.mistore.intefaces.CartUpdateListener
import com.example.mistore.model.CartProduct
import com.example.mistore.model.UserCartProduct

class CartAdapter(
    var context: Context,
    var listProduct: List<UserCartProduct> ,
    var cartUpdateListener: CartUpdateListener
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
//    var total: Double = 0.0
    var product = listProduct.toMutableList()

    private var total: Double =
        product.sumOf { it.productsModelItem!!.price * it.cartProduct!!.quntity }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productImage: ImageView = itemView.findViewById(R.id.img_cart_product_img)
        var txtProductName: TextView = itemView.findViewById(R.id.txt_cart_product_name)
        var txtProductPrice: TextView = itemView.findViewById(R.id.txt_cart_product_price)
        var txtMinus: TextView = itemView.findViewById(R.id.txt_cart_product_minus)
        var txtPlus: TextView = itemView.findViewById(R.id.txt_cart_product_plus)
        var txtQuntity: TextView = itemView.findViewById(R.id.txt_cart_product_count)
        var imgDeleteProduct:ImageView = itemView.findViewById(R.id.img_delete)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(context)
            .inflate(R.layout.recy_cart_layout, parent, false)
        return ViewHolder(view)


    }

    override fun getItemCount(): Int {

        return product.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context)
            .load(product.get(position).productsModelItem?.image)
            .into(holder.productImage)

        holder.txtProductName.text = "${product.get(position).productsModelItem?.title}"
        holder.txtProductPrice.text = "₹${product.get(position).productsModelItem?.price}"
        holder.txtQuntity.text = "${product.get(position).cartProduct?.quntity}"

        holder.txtPlus.setOnClickListener(View.OnClickListener {

            var value = product.get(position).cartProduct?.quntity
            if (value!! > 0) {
                value = value + 1
                cartUpdateListener.update(product.get(position).productsModelItem!!.id, value)
                holder.txtQuntity.text = "$value"
                product.get(position).cartProduct?.quntity = value
                var price = product.get(position).productsModelItem?.price
                total += price!!

                cartUpdateListener.getTotal(total)


            }


        })
        holder.txtMinus.setOnClickListener(View.OnClickListener {


            var value = product.get(position).cartProduct?.quntity
            if (value!! > 1) {
                value = value!! - 1
                cartUpdateListener.update(product.get(position).productsModelItem!!.id, value!!)
                holder.txtQuntity.text = "$value"
                product.get(position).cartProduct?.quntity = value!!

                var price = product.get(position).productsModelItem?.price
                total -= price!!

                cartUpdateListener.getTotal(total)

            }
        })

        cartUpdateListener.getTotal(total)

        holder.imgDeleteProduct.setOnClickListener(View.OnClickListener {


            var quantity = product.get(position).cartProduct?.quntity
            if (quantity!! > 0) {

                var price = product.get(position).productsModelItem?.price
                total -= (price!! * quantity!!)

                cartUpdateListener.getTotal(total)

            }




            product.get(position).cartProduct?.let { it1 ->
                cartUpdateListener.onDeleteCartListener(
                    it1,
                    position
                )
            }

        })

    }
    fun removeProduct(index:Int) {
        if (index != -1) {
            product.removeAt(index)
            notifyItemRemoved(index)
            notifyItemRangeChanged(index, itemCount)

        }
    }
}