package com.jjr.restaurantfinder.activities

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jakewharton.rxbinding2.widget.textChanges
import com.jjr.restaurantfinder.*
import com.jjr.restaurantfinder.model.Photo
import com.jjr.restaurantfinder.utils.ImageRequester
import com.jjr.restaurantfinder.utils.PostsDiffUtilCallback
import com.jjr.restaurantfinder.utils.RecyclerAdapter
import com.jjr.restaurantfinder.utils.SearchViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_search.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class SearchActivity : AppCompatActivity(), ImageRequester.ImageRequesterResponse {

    private val photosList: MutableList<Photo> = mutableListOf()
    private lateinit var imageRequester: ImageRequester
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var searchViewModel: SearchViewModel

//    private val filteredPhotos: MutableList<Photo> = mutableListOf()
//    private val oldFilteredPhotos: MutableList<Photo> = mutableListOf()

    private val disposable = CompositeDisposable()

    private val lastVisibleItemPosition: Int
        get() = if (recyclerViewSearch.layoutManager == linearLayoutManager) {
            linearLayoutManager.findLastVisibleItemPosition()
        } else {
            gridLayoutManager.findLastVisibleItemPosition()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)


        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewSearch.layoutManager = linearLayoutManager
        adapter = RecyclerAdapter(photosList)
        recyclerViewSearch.adapter = adapter
        setRecyclerViewScrollListener()

        imageRequester = ImageRequester(this)

        requestPhoto()
        searchViewModel.oldFilteredPhotos.addAll(photosList)

//        searchInput
//            .textChanges()
//            .debounce (200, TimeUnit.MILLISECONDS )
//            .subscribe{
//                this
//                    .search(it.toString())
//                    .subscribeOn(Schedulers.computation())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe{
//                        val diffResult = DiffUtil.calculateDiff(
//                            PostsDiffUtilCallback(
//                                oldFilteredPhotos,
//                                filteredPhotos
//                            )
//                        )
//                        oldFilteredPhotos.clear()
//                        oldFilteredPhotos.addAll(filteredPhotos)
//                        diffResult.dispatchUpdatesTo(adapter)
//                    }.addTo(disposable)
//            }.addTo(disposable)

        searchInput
            .textChanges()
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribe {
                searchViewModel
                    .search(it.toString())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        val diffResult = DiffUtil.calculateDiff(PostsDiffUtilCallback(searchViewModel.oldFilteredPhotos, searchViewModel.filteredPhotos))
                        searchViewModel.oldFilteredPhotos.clear()
                        searchViewModel.oldFilteredPhotos.addAll(searchViewModel.filteredPhotos)
                        diffResult.dispatchUpdatesTo(adapter)
                    }.addTo(disposable)
            }.addTo(disposable)


    }

    private fun setRecyclerViewScrollListener() {
        recyclerViewSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (!imageRequester.isLoadingData && totalItemCount == lastVisibleItemPosition + 1) {
                    requestPhoto()
                    searchViewModel.oldFilteredPhotos.addAll(photosList)
                }
            }
        })
    }

    private fun requestPhoto() {
        try {
            imageRequester.getPhoto()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun receivedNewPhoto(newPhoto: Photo) {
        runOnUiThread {
            photosList.add(newPhoto)
            adapter.notifyItemInserted(photosList.size-1)
        }
    }

//    private fun search(query: String): Completable = Completable.create { it ->
//        val wanted = filteredPhotos.filter {
//            it.explanation.contains(query)
//        }.toList()
//
//        filteredPhotos.clear()
//        filteredPhotos.addAll(wanted)
//        it.onComplete()
//    }

}
