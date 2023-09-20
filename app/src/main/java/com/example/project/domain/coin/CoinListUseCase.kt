package com.example.project.domain.coin

import com.example.project.network.UseCase
import com.example.project.repository.coinService.CoinRepository
import com.example.project.repository.coinService.reqres.Coin
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class CoinListUseCase @Inject constructor(private val repository: CoinRepository) :
    UseCase<String, Response<List<Coin>>>() {

    override fun execute(parameter: String?): Flow<Response<List<Coin>>> {
        return repository.getCoinList()
    }

}