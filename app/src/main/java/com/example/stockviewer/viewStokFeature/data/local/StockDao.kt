package com.example.stockviewer.viewStokFeature.data.local

import androidx.room.*
import okhttp3.ResponseBody
import java.util.concurrent.Flow

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserCompanyListinngs(companyListings: List<CompanyEntity>)

    @Query("delete from companyentity")
    suspend fun clearCompanyListings()

    @Query("""select * from CompanyEntity where lower(name) like '%' || lower(:query)  || '%' or upper(:query)==symbol """)
    suspend fun searchCompanyList(query: String): List<CompanyEntity>
}