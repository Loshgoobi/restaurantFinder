package com.jjr.restaurantfinder.model

data class Restaurant(
    val id: Int,
    val nom: String,
    val adresse: String,
    val note: Float,
    val type: String,
    val numero: String,
    val url: String,
    val created_at: String,
    val updated_at: String,
    val plat: List<Plat>
    )

data class Plat(
    val id: Int,
    val nom: String,
    val adresse: String,
    val note: Float,
    val created_at: String,
    val updated_at: String,
    val pivot: Pivot
)

class Pivot {
    val resto_id: Int = 0
    val plat_id: Int = 0
}