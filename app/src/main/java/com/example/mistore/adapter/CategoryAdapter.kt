package com.example.mistore.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mistore.R
import com.example.mistore.intefaces.CategorySelectListener

class CategoryAdapter(
    val context: Context, val list: ArrayList<String>,
    val categorySelectListener: CategorySelectListener
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    var selectedValue:Int = 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtCategory: TextView = itemView.findViewById(R.id.txt_category_list)
        var view:View = itemView.findViewById(R.id.view2)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(context)
            .inflate(R.layout.recy_category_llist, parent, false)

        return ViewHolder(view)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        if(selectedValue == position)
        {
            holder.txtCategory.setTextColor(Color.parseColor("#FFC107"))
            holder.view.visibility = View.VISIBLE
        }
        else{

            holder.txtCategory.setTextColor(Color.BLACK)
            holder.view.visibility = View.INVISIBLE


        }

        holder.txtCategory.text = "${list.get(position)}"
        holder.txtCategory.setOnClickListener(View.OnClickListener {
            selectedValue = position

            categorySelectListener.onCategorySelectListener(list.get(position))
            notifyDataSetChanged()

        })

    }
    fun notifyvaue(){
        notifyDataSetChanged()

    }

}