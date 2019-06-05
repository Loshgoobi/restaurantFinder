package com.jjr.restaurantfinder.utils

import android.app.Activity
import android.content.Context
import android.net.Uri.Builder
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jjr.restaurantfinder.R
import com.jjr.restaurantfinder.model.Restaurant
import okhttp3.*
import org.json.JSONException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ImageRequester(listeningActivity: Activity) {

    interface ImageRequesterResponse {
        fun receivedNewRestaurant(newRestaurant: MutableList<Restaurant>)
    }

    val gson = GsonBuilder().setPrettyPrinting().create()
    private val calendar: Calendar = Calendar.getInstance()
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private val responseListener: ImageRequesterResponse
    private val context: Context
    private val client: OkHttpClient
    var isLoadingData: Boolean = false
        private set

    init {
        responseListener = listeningActivity as ImageRequesterResponse
        context = listeningActivity.applicationContext
        client = OkHttpClient()
    }

    fun getRestaurant() {

        val date = dateFormat.format(calendar.time)

        val urlRequest = Builder().scheme(URL_SCHEME)
            .authority(URL_AUTHORITY)
            .appendPath(URL_PATH_1)
            .appendPath(URL_PATH_2)
            .appendQueryParameter(URL_QUERY_PARAM_DATE_KEY, date)
            .appendQueryParameter(
                URL_QUERY_PARAM_API_KEY, context.getString(
                    R.string.api_key
                ))
            .build().toString()

        val request = Request.Builder().url(urlRequest).build()
        isLoadingData = true

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                isLoadingData = false
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                try {
                    val photoJSON = response.body()!!.string()

                    var restaurant: MutableList<Restaurant>
                    restaurant = gson.fromJson(photoJSON, object : TypeToken<MutableList<Restaurant>>() {}.type)

                    if (restaurant != null) {
                        responseListener.receivedNewRestaurant(restaurant)
                        isLoadingData = false
                    } else {
                        getRestaurant()
                    }
                } catch (e: JSONException) {
                    isLoadingData = false
                    e.printStackTrace()
                }

            }
        })
    }

    companion object {
        private val URL_SCHEME = "https"
        private val URL_AUTHORITY = "api.nasa.gov"
        private val URL_PATH_1 = "api"
        private val URL_PATH_2 = ""
        private val URL_QUERY_PARAM_DATE_KEY = "date"
        private val URL_QUERY_PARAM_API_KEY = "api_key"
    }
}