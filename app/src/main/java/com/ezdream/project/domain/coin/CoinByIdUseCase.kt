package com.ezdream.project.domain.coin

import com.ezdream.project.network.UseCase
import com.ezdream.project.repository.coinService.CoinRepository
import com.ezdream.project.repository.coinService.reqres.CoinDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoinByIdUseCase @Inject constructor(private val repository: CoinRepository) :
    UseCase<String, CoinDetail>() {

    override fun execute(parameter: String?): Flow<CoinDetail> {

        return repository.getCoinById(parameter)
    }

}