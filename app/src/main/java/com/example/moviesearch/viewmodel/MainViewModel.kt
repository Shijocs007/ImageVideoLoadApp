package com.example.moviesearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.data.model.ImageData
import com.example.moviesearch.data.repository.MainRepository
import com.example.moviesearch.utils.ApiException
import com.example.moviesearch.utils.Coroutines
import com.example.moviesearch.utils.NoInternetException

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private var imageData = MutableLiveData<ImageData>()
    private var errorMessage = MutableLiveData<String>()

    fun getImageData() {
        Coroutines.main {
            try {
                val result = mainRepository.getImageData()
                result.let {
                    imageData.value = it
                    mainRepository.insertImageData(it)
                }
            } catch (e: ApiException) {
                errorMessage.value = e.message
            } catch (e : NoInternetException) {
                imageData.value = mainRepository.getImageDataDb()
                errorMessage.value = e.message
            }
        }
    }

    fun getImageData(date : String) {
        Coroutines.main {
            try {
                val result = mainRepository.getImageData(date)
                result.let {
                    imageData.value = it
                    mainRepository.insertImageData(it)
                }
            } catch (e: ApiException) {
                errorMessage.value = e.message
            } catch (e : NoInternetException) {
                imageData.value = mainRepository.getImageDataDb(date)
                errorMessage.value = e.message
            }
        }
    }

    fun getImageDataLivedata() : LiveData<ImageData> {
        return imageData
    }


    fun getErrorMessage() : LiveData<String> {
        return errorMessage
    }
}