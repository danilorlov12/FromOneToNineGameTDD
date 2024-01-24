package com.orlovdanylo.fromonetoninegame.tip

sealed class TipResult {
    data class AvailablePairs(val pairs: List<Pair<Int, Int>>) : TipResult()
    object NotFound : TipResult()
}