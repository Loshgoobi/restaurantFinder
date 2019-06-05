package com.jjr.restaurantfinder.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jjr.restaurantfinder.model.Photo
import com.jjr.restaurantfinder.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photo.*
import org.jetbrains.anko.makeCall

class PhotoActivity : AppCompatActivity() {
    private val TAG = "TEEESTTTTT"
    private val RECORD_REQUEST_CODE = 101
    private var selectedPhoto: Photo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_photo)

        selectedPhoto = intent.getSerializableExtra(PHOTO_KEY) as Photo
        Picasso.get().load(selectedPhoto?.url).into(photoImageView)

        photoDescription?.text = selectedPhoto?.explanation
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
            Log.i(TAG, "Permission to record denied")
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
