package com.jjr.restaurantfinder.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.RatingBar
import com.jjr.restaurantfinder.R
import com.jjr.restaurantfinder.model.Plat
import com.jjr.restaurantfinder.model.Restaurant
import com.jjr.restaurantfinder.utils.RecyclerPlatsAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photo.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.makeCall

class PhotoActivity : AppCompatActivity() {

    private val RECORD_REQUEST_CODE = 101
    private var selectedPhoto: Restaurant? = null
    private var dishList: MutableList<Plat> = mutableListOf()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: com.jjr.restaurantfinder.utils.RecyclerPlatsAdapter

    private val lastVisibleItemPosition: Int
        get() = if (recyclerView.layoutManager == linearLayoutManager) {
            linearLayoutManager.findLastVisibleItemPosition()
        } else {
            gridLayoutManager.findLastVisibleItemPosition()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_photo)

        selectedPhoto = intent.getStringExtra(PHOTO_KEY) as Restaurant
        Picasso.get().load(selectedPhoto?.url).into(photoImageView)

        restaurantName?.text = selectedPhoto?.nom
        restaurantAdress?.text = selectedPhoto?.adresse

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewDish.layoutManager = linearLayoutManager
        adapter = RecyclerPlatsAdapter(selectedPhoto!!)
        recyclerViewDish.adapter = adapter



        val ratingBar = findViewById<RatingBar>(R.id.ratingBar1)
        ratingBar.rating = 4F


        makeRequest()
        setupPermissions()
        callButton.setOnClickListener{
            makeCall("0667342472")
        }
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CALL_PHONE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.CALL_PHONE),
            RECORD_REQUEST_CODE)
    }

    companion object {
        private val PHOTO_KEY = "PHOTO"
    }
}
