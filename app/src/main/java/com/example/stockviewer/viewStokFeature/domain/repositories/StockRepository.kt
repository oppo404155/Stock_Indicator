package com.example.stockviewer.viewStokFeature.domain.repositories

import com.example.stockviewer.viewStokFeature.domain.models.CompanyListingModel
import com.example.stockviewer.viewStokFeature.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(
        fetchFromAPi:Boolean,
        query:String
    ):Flow<Resource<List<CompanyListingModel>>>
}