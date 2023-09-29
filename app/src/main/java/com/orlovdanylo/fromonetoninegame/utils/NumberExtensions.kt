package com.orlovdanylo.fromonetoninegame.utils

infix fun Long.smallestNonzero(other: Long): Long {
    return if (other == 0L || (this < other)) this else other
}