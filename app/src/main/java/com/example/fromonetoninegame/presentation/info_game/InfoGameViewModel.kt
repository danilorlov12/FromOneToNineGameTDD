package com.example.fromonetoninegame.presentation.info_game

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.fromonetoninegame.R
import com.example.fromonetoninegame.base.BaseViewModel
import com.example.fromonetoninegame.models.PageInfo

class InfoGameViewModel(app: Application) : BaseViewModel(app) {

    val currentPage: MutableLiveData<PageInfo> = MutableLiveData()

    fun initFirstPage() {
        currentPage.value = pages.getValue(0)
    }

    fun nextPage() {
        val currentPageKey = findCurrentPageKey()
        if (currentPageKey == 5) {
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

    companion object {
        private val pages = HashMap<Int, PageInfo>().apply {
            put(0, PageInfo(R.string.purpose_of_the_game, R.drawable.ic_info))
            put(1, PageInfo(R.string.tap_on_pair, R.drawable.ic_info))
            put(2, PageInfo(R.string.remove_vertically_horizontally, R.drawable.ic_info))
            put(3, PageInfo(R.string.remove_in_between, R.drawable.ic_info))
            put(4, PageInfo(R.string.remove_last_first, R.drawable.ic_info))
            put(5, PageInfo(R.string.tap_add, R.drawable.ic_info))
        }
    }
}
