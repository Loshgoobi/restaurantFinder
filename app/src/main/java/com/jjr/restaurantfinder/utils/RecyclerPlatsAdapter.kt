package com.jjr.restaurantfinder.utils

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.jjr.restaurantfinder.R
import com.jjr.restaurantfinder.model.Plat
import com.jjr.restaurantfinder.model.Restaurant
import kotlinx.android.synthetic.main.recyclerviewplats_item_row.view.*

class RecyclerPlatsAdapter(private val restaurants: Restaurant) : RecyclerView.Adapter<RecyclerPlatsAdapter.DishHolder>() {

    private var plats: List<Plat> = restaurants.plat

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishHolder {
        val inflatedView = parent.inflate(R.layout.recyclerviewplats_item_row, false)
        return DishHolder(inflatedView)
    }

    override fun getItemCount(): Int = plats.size

    override fun onBindViewHolder(holder: DishHolder, position: Int) {
        val itemPlat = plats[position]
        holder.bindPlat(itemPlat)
    }

    //1
    class DishHolder(private val view: View) : RecyclerView.ViewHolder(view){
        //2
        private var plats: Plat? = null


        fun bindPlat(plats: Plat) {
            this.plats = plats
            view.textView.text = plats.nom
            view.ratingBar.rating = plats.note
        }


        companion object {
            //5
            private val PLAT_KEY = "PHOTO"
        }
    }
}