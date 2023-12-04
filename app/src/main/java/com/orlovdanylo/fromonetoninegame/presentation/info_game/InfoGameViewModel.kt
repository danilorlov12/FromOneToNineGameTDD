package com.orlovdanylo.fromonetoninegame.presentation.info_game

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.orlovdanylo.fromonetoninegame.Repositories
import com.orlovdanylo.fromonetoninegame.base.BaseViewModel
import com.orlovdanylo.fromonetoninegame.data.info_pages.InfoPage

class InfoGameViewModel : BaseViewModel() {

    private val infoPagesRepository = Repositories.infoPagesRepository

    private val pages = infoPagesRepository.fetchPages()
    val currentPage: MutableLiveData<InfoPage> = MutableLiveData()

    fun initFirstPage() {
        currentPage.value = pages.getValue(0)
    }

    fun nextPage() {
        val currentPageKey = findCurrentPageKey()
        if (currentPageKey == 4) {
            closeFragment.value = true
        } else {
            currentPage.value = pages.getValue(findCurrentPageKey() + 1)
        }
    }

    fun previousPage() {
        val currentPageKey = findCurrentPageKey()
        if (currentPageKey == 0) {
            closeFragment.value = true
        } else {
            currentPage.value = pages.getValue(findCurrentPageKey() - 1)
        }
    }

    private fun findCurrentPageKey(): Int {
        return pages.entries.firstOrNull {
            currentPage.value == it.value
        }?.key ?: 0
    }
}
