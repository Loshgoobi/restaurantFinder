package com.jjr.restaurantfinder.utils

import android.arch.lifecycle.ViewModel
import com.jjr.restaurantfinder.model.Photo
import io.reactivex.Completable

class SearchViewModel(): ViewModel() {


    val photosList: MutableList<Photo> = mutableListOf()


    val filteredPhotos: MutableList<Photo> = mutableListOf()
    val oldFilteredPhotos: MutableList<Photo> = mutableListOf()


    init {
        oldFilteredPhotos.addAll(photosList)
    }

    fun search(query: String): Completable = Completable.create { it ->
        val wanted = filteredPhotos.filter {
            it.explanation.contains(query)
        }.toList()

        filteredPhotos.clear()
        filteredPhotos.addAll(wanted)
        it.onComplete()
    }
}