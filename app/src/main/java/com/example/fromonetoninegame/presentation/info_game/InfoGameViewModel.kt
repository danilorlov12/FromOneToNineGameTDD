package com.example.fromonetoninegame.presentation.info_game

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.fromonetoninegame.base.BaseViewModel
import com.example.fromonetoninegame.models.PageInfo
import com.example.fromonetoninegame.utils.InfoPageUtils

class InfoGameViewModel(application: Application) : BaseViewModel(application) {

    val currentPage: MutableLiveData<PageInfo> = MutableLiveData()

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
        return InfoPageUtils.pages.entries.firstOrNull {
            currentPage.value == it.value
        }?.key ?: 0
    }

    companion object {
        private val pages = InfoPageUtils.pages
    }
}
