package com.xu.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val status: String,val places:List<Place>)

//位置
data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)

//坐标
data class Location(val lng: String, val lat: String)
