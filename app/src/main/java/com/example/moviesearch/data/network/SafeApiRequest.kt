package com.example.moviesearch.data.network

import com.example.moviesearch.utils.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.lang.StringBuilder

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call : suspend() -> Response<T>) : T{
        val response = call.invoke()

        if(response.isSuccessful) {
            return response.body()!!
        } else {
            val errorResponse = response.errorBody().toString()
            val message = StringBuilder()
            errorResponse?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                } catch (e : JSONException) {

                }
            }
            message.append("Error code : ${response.code()}")
            throw ApiException(message.toString())
        }
    }
}