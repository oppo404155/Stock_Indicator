package com.example.stockviewer.viewStokFeature.presentation.companyListScreen

import com.example.stockviewer.viewStokFeature.domain.models.CompanyListingModel

data class CompanyListingsState(
    val companies: List<CompanyListingModel> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
