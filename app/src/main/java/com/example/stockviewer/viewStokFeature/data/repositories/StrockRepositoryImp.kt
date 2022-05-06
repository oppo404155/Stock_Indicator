package com.example.stockviewer.viewStokFeature.data.repositories

import com.example.stockviewer.viewStokFeature.data.csv.CompanyListingsParser
import com.example.stockviewer.viewStokFeature.data.csv.CsvParser
import com.example.stockviewer.viewStokFeature.data.local.StockDB
import com.example.stockviewer.viewStokFeature.data.mappers.toDomain
import com.example.stockviewer.viewStokFeature.data.mappers.toEntity
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
class StockRepositoryImp @Inject constructor(
    val api: StockAPI,
    val database: StockDB,
    val parser: CsvParser<CompanyListingModel>
) : StockRepository {
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
            val remoteListings = try {
                val resposne = api.getallList()
                parser.parse(resposne.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(null, error = "could not load the data "))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(
                    Resource.Error(error = "Could not load data")
                )
                null
            }

            remoteListings?.let { listings ->

                database.getstockDao().clearCompanyListings()
                database.getstockDao()
                    .inserCompanyListinngs(listings.map { model -> model.toEntity() })
                emit(
                    Resource.Successes(
                        data = database.getstockDao().searchCompanyList("")
                            .map { entity -> entity.toDomain() })
                )
                emit(Resource.Loading(false))

            }

        }
    }
}