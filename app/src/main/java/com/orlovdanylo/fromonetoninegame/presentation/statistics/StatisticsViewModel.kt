package com.orlovdanylo.fromonetoninegame.presentation.statistics

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orlovdanylo.fromonetoninegame.base.BaseViewModel
import com.orlovdanylo.fromonetoninegame.data.statistics.StatisticRepositoryImpl
import com.orlovdanylo.fromonetoninegame.domain.model.StatisticsModel
import kotlinx.coroutines.launch

class StatisticsViewModel(app: Application) : BaseViewModel(app) {

    private val statisticsRepository = StatisticRepositoryImpl(app)

    val statistics: MutableLiveData<StatisticsModel> = MutableLiveData()

    fun loadStatistics() {
        viewModelScope.launch {
            statistics.value = statisticsRepository.getStatistics()
        }
    }
}