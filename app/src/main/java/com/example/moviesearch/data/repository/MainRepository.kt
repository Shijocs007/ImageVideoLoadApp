package com.example.moviesearch.data.repository

import com.example.moviesearch.data.db.AppDataBase
import com.example.moviesearch.data.model.ImageData
import com.example.moviesearch.data.network.MyApi
import com.example.moviesearch.data.network.SafeApiRequest


class MainRepository(
    private val api : MyApi,
    private val db : AppDataBase
) : SafeApiRequest() {


    suspend fun getImageData() : ImageData {
        return apiRequest { api.getImageData() }
    }

    suspend fun getImageData(date : String) : ImageData {
        return apiRequest { api.getImageData(date) }
    }

    suspend fun insertImageData(imageData: ImageData) = db.getMyDao().insertData(imageData)

    suspend fun getImageDataDb() = db.getMyDao().geImagetData()

    suspend fun getImageDataDb(date: String) = db.getMyDao().geImagetData(date)

}