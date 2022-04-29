package com.example.stockviewer.viewStokFeature.utils

sealed class  Resource<T>(val data:T?=null,val error:String?=null){
  class Successes<T>( data: T?):Resource<T>(data=data)
class Loading<T>(val isLoading:Boolean=true):Resource<T>()
    class Error<T>(data: T?=null,error: String?):Resource<T>(data=data, error = error)
}
