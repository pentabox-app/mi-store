package com.example.mistore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mistore.R
import com.example.mistore.intefaces.ProductSelectListener
import com.example.mistore.model.ProductsModel
import com.example.mistore.model.ProductsModelItem

class AdapterProducts(
    val contex: Context,
    var products: ProductsModel,
    var productSelectListener: ProductSelectListener
) : RecyclerView.Adapter<AdapterProducts.ViewHolder>() {

    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtProductName: TextView = itemView.findViewById(R.id.txt_product_name)
        var txtProductPrice: TextView = itemView.findViewById(R.id.txt_product_price)
        var imgProduct: ImageView = itemView.findViewById(R.id.img_product)
        var btnAddCart: Button = itemView.findViewById(R.id.btn_add_to_cart)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(contex)
            .inflate(R.layout.recy_product_layout, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return products.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(contex)
            .setDefaultRequestOptions(requestOptions)
            .load(products.get(position).image)
            .override(300,300)

            .into(holder.imgProduct)

        holder.btnAddCart.setOnClickListener(View.OnClickListener {

            productSelectListener.selectProductListener(products.get(position))

        })

        holder.txtProductName.text = "${products.get(position).title}"
        holder.txtProductPrice.text = "₹ ${products.get(position).price}"


    }
    fun addCategoryProducts(products:List<ProductsModelItem>){
        this.products.clear()
        this.products.apply {
            addAll(products)
        }
        notifyDataSetChanged()

    }

}