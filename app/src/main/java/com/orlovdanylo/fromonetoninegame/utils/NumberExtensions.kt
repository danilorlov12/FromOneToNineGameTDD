package com.orlovdanylo.fromonetoninegame.utils

infix fun Long.smallestNonzero(other: Long): Long {
    return if (other == 0L || (this < other)) this else other
}

infix fun List<*>.calculatePosition(oldPosition: Int): Int {
    return if (size - 1 <= oldPosition) 0 else oldPosition + 1
}