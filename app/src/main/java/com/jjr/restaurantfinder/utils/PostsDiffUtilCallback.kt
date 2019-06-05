package com.jjr.restaurantfinder.utils

import android.support.v7.util.DiffUtil
import com.jjr.restaurantfinder.model.Photo

class PostsDiffUtilCallback(private val oldList: List<Photo>, private val newList: List<Photo>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = true
}