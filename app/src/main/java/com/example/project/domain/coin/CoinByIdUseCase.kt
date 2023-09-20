package com.example.project.domain.coin

import com.example.project.network.UseCase
import com.example.project.repository.coinService.CoinRepository
import com.example.project.repository.coinService.reqres.CoinDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoinByIdUseCase @Inject constructor(private val repository: CoinRepository) :
    UseCase<String, CoinDetail>() {

    override fun execute(parameter: String?): Flow<CoinDetail> {

        return repository.getCoinById(parameter)
    }

}