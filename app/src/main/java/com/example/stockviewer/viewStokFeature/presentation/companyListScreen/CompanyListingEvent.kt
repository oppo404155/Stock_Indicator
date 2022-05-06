package com.example.stockviewer.viewStokFeature.presentation.companyListScreen

sealed class CompanyListingEvent{
    object Refresh:CompanyListingEvent()
    data class OnQueryChanged(val query:String):CompanyListingEvent()

}
