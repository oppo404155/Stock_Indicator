package com.example.stockviewer.viewStokFeature.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyEntity(
    val name: String,
    val symbol:String,
    val exchange:String,
    @PrimaryKey
    val id:Int?=null
)
