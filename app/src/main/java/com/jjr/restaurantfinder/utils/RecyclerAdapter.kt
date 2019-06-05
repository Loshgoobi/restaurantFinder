package com.jjr.restaurantfinder.utils

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.jjr.restaurantfinder.R
import com.jjr.restaurantfinder.activities.PhotoActivity
import com.jjr.restaurantfinder.model.Restaurant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

class RecyclerAdapter(private val restaurants: MutableList<Restaurant>) : RecyclerView.Adapter<RecyclerAdapter.PhotoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
        return PhotoHolder(inflatedView)
    }

    override fun getItemCount(): Int = restaurants.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val itemPhoto = restaurants[position]
        holder.bindPhoto(itemPhoto)
    }

    //1
    class PhotoHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        //2
        private var restaurant: Restaurant? = null

        //3
        init {
            view.setOnClickListener(this)
        }

        fun bindPhoto(restaurant1: Restaurant) {
            this.restaurant = restaurant1
            Picasso.get().load(restaurant1.url).into(view.itemImage)
            view.itemName.text = restaurant1.nom
            view.itemDescription.text = restaurant1.type
        }

        //4
        override fun onClick(v: View) {
            val context = itemView.context
            val showPhotoIntent = Intent(context, PhotoActivity::class.java)
            showPhotoIntent.putExtra(PHOTO_KEY, restaurant?.url)
            context.startActivity(showPhotoIntent)
        }

        companion object {
            //5
            private val PHOTO_KEY = "PHOTO"
        }
    }

}