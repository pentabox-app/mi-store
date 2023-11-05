package com.example.mistore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.cheezycode.daggermvvm.viewmodels.MainViewModel
import com.example.mistore.R
import com.example.mistore.adapter.CartAdapter
import com.example.mistore.databinding.ActivityCartBinding
import com.example.mistore.intefaces.CartUpdateListener
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat


@AndroidEntryPoint
class CartActivity : AppCompatActivity() {


    lateinit var mainViewmodel:MainViewModel
    lateinit var binding: ActivityCartBinding
    lateinit var adapter: CartAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_cart)
        mainViewmodel = ViewModelProvider(this).get(MainViewModel::class.java)
        
        mainViewmodel.cartLiveData.observe(this, Observer {
            adapter  = CartAdapter(this,it, cartUpdateListener = object : CartUpdateListener {
                override fun update(id: Int, newValue: Int) {
                    mainViewmodel.updateCartProductQuantity(id,newValue)
                }

                override fun getTotal(total: Double) {
                    val decimalFormat = DecimalFormat("₹#,##0.00")
                    val formattedPrice = decimalFormat.format(total)
                    binding.txtCartTotal.text = "Total Pay ₹$formattedPrice"
                }

            })
            binding.recyCart.adapter = adapter
            binding.recyCart.layoutManager  = LinearLayoutManager(this)
            binding.recyCart.setHasFixedSize(true)


        })
        binding.txtOnbackPress.setOnClickListener(View.OnClickListener {
            onBackPressedDispatcher.onBackPressed()

        })

    }


}