package com.example.project6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DishAdapter(private val dishes: List<MainActivity.Dish>) : RecyclerView.Adapter<DishAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dishImage: ImageView
        val dishName: TextView
        val dishArea: TextView

        init {
            dishImage = view.findViewById(R.id.dish_image)
            dishName = view.findViewById(R.id.dish_name)
            dishArea = view.findViewById(R.id.dish_area)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dish_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = dishes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dish = dishes[position]

        Glide.with(holder.itemView)
            .load(dish.imageUrl)
            .centerCrop()
            .into(holder.dishImage)

        holder.dishName.text = dish.dishName
        holder.dishArea.text = dish.dishArea

        holder.dishImage.setOnClickListener() {
            Toast.makeText(holder.itemView.context, dish.dishName, Toast.LENGTH_SHORT).show()
        }
    }
}