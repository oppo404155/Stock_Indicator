package com.example.stockviewer.viewStokFeature.data.repositories

import androidx.room.Database
import com.example.stockviewer.viewStokFeature.data.local.StockDB
import com.example.stockviewer.viewStokFeature.data.local.StockDao
import com.example.stockviewer.viewStokFeature.data.mappers.toDomain
import com.example.stockviewer.viewStokFeature.data.remote.StockAPI
import com.example.stockviewer.viewStokFeature.domain.models.CompanyListingModel
import com.example.stockviewer.viewStokFeature.domain.repositories.StockRepository
import com.example.stockviewer.viewStokFeature.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StrockRepositoryImp @Inject constructor(private val api:StockAPI, private val database:StockDB):StockRepository{
    override suspend fun getCompanyListings(
        fetchFromAPi: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListingModel>>> {
        return flow {
            emit(Resource.Loading(true))
            val locaListings = database.getstockDao().searchCompanyList(query)
            emit(Resource.Successes(data = locaListings.map { entity -> entity.toDomain() }))
            val isLocalDBIsEmpty = locaListings.isEmpty() && query.isBlank()
            val shouldLoadFromCash = !isLocalDBIsEmpty && !fetchFromAPi
            if (shouldLoadFromCash) {
                emit(Resource.Loading(false))
                return@flow
            }
            try {
                val resposne=api.getallList()

            }catch (e:IOException){
                e.printStackTrace()
                emit(Resource.Error(null, error = "could not load the data "))
            }catch (e:HttpException){
                e.printStackTrace()
                emit(Resource.Error(error = "Could not load data"))
            }

        }
    }
}