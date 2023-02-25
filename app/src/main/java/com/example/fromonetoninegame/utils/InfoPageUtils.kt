package com.example.fromonetoninegame.utils

import com.example.fromonetoninegame.R
import com.example.fromonetoninegame.models.Model
import com.example.fromonetoninegame.models.PageInfo

object InfoPageUtils {

    private val secondPageInfo = listOf(
        "0", "0", "0", "0", "0",
        "2", "8", "0", "1", "1",
        "0", "0", "0", "0", "0"
    )

    private val thirdPageInfo = listOf(
        "6", "0", "0", "0", "0",
        "6", "0", "1", "9", "0",
        "0", "0", "0", "0", "0"
    )

    private val fourthPageInfo = listOf(
        "5", "0", "1", "0", "9",
        "0", "0", "0", "0", "0",
        "5", "0", "0", "0", "0",
    )

    private val fifthPageInfo = listOf(
        "0", "0", "0", "7", "0",
        "0", "3", "0", "0", "0",
        "0", "0", "0", "0", "0"
    )

    val pages = HashMap<Int, PageInfo>().apply {
        put(
            0, PageInfo(
                R.string.purpose_of_the_game,
                emptyList()
            )
        )
        put(
            1, PageInfo(
                R.string.tap_on_pair,
                convertToModelList(secondPageInfo)
            )
        )
        put(
            2, PageInfo(
                R.string.remove_vertically_horizontally,
                convertToModelList(thirdPageInfo)
            )
        )
        put(
            3, PageInfo(
                R.string.remove_in_between,
                convertToModelList(fourthPageInfo)
            )
        )
        put(
            4, PageInfo(
                R.string.remove_last_first,
                convertToModelList(fifthPageInfo)
            )
        )
    }


    private fun convertToModelList(list: List<String>): List<Model> {
        return list.mapIndexed { index, num ->
            Model(index, num.toInt(), num == "0")
        }
    }
}