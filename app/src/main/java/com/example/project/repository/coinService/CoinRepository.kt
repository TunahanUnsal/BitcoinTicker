package com.example.project.repository.coinService

import com.example.project.repository.coinService.reqres.Coin
import com.example.project.repository.coinService.reqres.CoinDetail
import com.example.project.repository.coinService.reqres.PriceModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CoinRepository {

    fun getCoinList(): Flow<Response<List<Coin>>>
    fun getCoinById(id : String?): Flow<CoinDetail>
    fun getPriceById(id : String?): Flow<Response<List<PriceModel>>>

}