package com.example.mistore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cheezycode.daggermvvm.viewmodels.MainViewModel
import com.example.mistore.adapter.AdapterProducts
import com.example.mistore.adapter.CategoryAdapter
import com.example.mistore.databinding.ActivityMainBinding
import com.example.mistore.intefaces.CategorySelectListener
import com.example.mistore.intefaces.ProductSelectListener
import com.example.mistore.model.CartProduct
import com.example.mistore.model.ProductsModel
import com.example.mistore.model.ProductsModelItem
import com.example.mistore.ui.CartActivity
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var adapterProducts: AdapterProducts
    lateinit var categoryList: ArrayList<String>
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var allProducts: ProductsModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        initlize()
        searchView()


        //observer
        mainViewModel.productsLiveData.observe(this, Observer {


            // for get all products
            allProducts.clear()
            allProducts.addAll(it)
            updateProduct(productsModel = it)


            //get all categories
            categoryList.clear()
            categoryList.addAll(it.map { it.category }.distinct())
            updateCategory(categoryList)


//            categoryAdapter.notifyvaue()


        })
        binding.txtCartButton.setOnClickListener(View.OnClickListener {

            startActivity(Intent(this,CartActivity::class.java))

        })
    }

    fun updateProduct(productsModel: ProductsModel) {

        adapterProducts = AdapterProducts(this, productsModel, object : ProductSelectListener {
            override fun selectProductListener(productsModelItem: ProductsModelItem) {

                mainViewModel.addToCart(CartProduct(productId = productsModelItem.id, cartId = 0))

            }
        })

        binding.recy.adapter = adapterProducts
        binding.recy.layoutManager = GridLayoutManager(this, 2)
    }

    fun updateCategory(list: ArrayList<String>) {
        categoryAdapter = CategoryAdapter(this, categoryList, object : CategorySelectListener {
            override fun onCategorySelectListener(category: String) {
                var categoryProducts: ProductsModel = ProductsModel()
                categoryProducts.addAll(allProducts)

                val fiterProduct = if (category == "All") {
                    categoryProducts
                } else {
                    categoryProducts.filter { it.category == category }
                }
                adapterProducts.addCategoryProducts(fiterProduct)


            }
        })
        categoryAdapter.list.add(0, "All")

        binding.recyCategory.adapter = categoryAdapter
        binding.recyCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyCategory

    }

    fun initlize() {

        categoryList = ArrayList()
        allProducts = ProductsModel()
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    fun searchView() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.recyCategory.visibility = View.GONE
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
                if (s!!.trim().isEmpty()) {
                    binding.recyCategory.visibility = View.VISIBLE
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun filter(text: String) {
        // Use filter and lowerCase() directly on the list to create a filtered list
        val filteredList = allProducts.filter {
            it.title.lowercase().contains(text.lowercase()) || it.id.toString().lowercase()
                .contains(text.lowercase())
        }

        if (filteredList.isEmpty()) {
            // Use string resource for better localization
            Toast.makeText(this, "No Data Found ", Toast.LENGTH_SHORT).show()
            adapterProducts.addCategoryProducts(filteredList)


        } else {
            // Use a function extension for cleaner code
            adapterProducts.addCategoryProducts(filteredList)
        }
    }

}