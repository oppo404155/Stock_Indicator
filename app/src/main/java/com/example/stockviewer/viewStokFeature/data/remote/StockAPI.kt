package com.example.stockviewer.viewStokFeature.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET

interface StockAPI {
    @GET("query?function=LISTING_STATUS")
    suspend fun getallList(
        @retrofit2.http.Query("apikey")apikey:String= API_KEY):ResponseBody
    companion object{
        const val API_KEY="0FI6KT6RGN4M09SN"
        const val BASE_URL="https://www.alphavantage.co"
    }
}