package com.example.stockviewer.viewStokFeature.data.mappers

import com.example.stockviewer.viewStokFeature.data.local.CompanyEntity
import com.example.stockviewer.viewStokFeature.domain.models.CompanyListingModel

fun CompanyListingModel.toEntity() =
    CompanyEntity(name = name, symbol = symbol, exchange = exchange)

fun CompanyEntity.toDomain() =
    CompanyListingModel(name = name, symbol = symbol, exchange = exchange)