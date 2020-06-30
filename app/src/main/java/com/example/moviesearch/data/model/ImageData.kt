package com.example.moviesearch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_ID = 1

@Entity
data class ImageData (

	@SerializedName("copyright") val copyright : String? = "",
	@SerializedName("date") val date : String? = "",
	@SerializedName("explanation") val explanation : String? = "",
	@SerializedName("hdurl") val hdurl : String?,
	@SerializedName("media_type") val media_type : String? = "",
	@SerializedName("service_version") val service_version : String? = "",
	@SerializedName("title") val title : String? = "",
	@SerializedName("url") val url : String? = ""
){
	@PrimaryKey(autoGenerate = true)
	var uid : Int = CURRENT_ID
}