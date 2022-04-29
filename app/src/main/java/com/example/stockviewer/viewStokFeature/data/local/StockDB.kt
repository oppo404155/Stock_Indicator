package com.example.stockviewer.viewStokFeature.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = arrayOf(CompanyEntity::class), version = 1)
abstract class StockDB:RoomDatabase() {
    abstract  fun getstockDao():StockDao
}