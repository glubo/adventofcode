package cz.glubo.adventofcode.y2023.daylast4

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.fold

/**
 * https://adventofcode.com/2022/day/4
 */

private fun String.toRange() =
    this.split("-").let {
        it.first().toInt() to it.last().toInt()
    }

suspend fun Flow<String>.y2023dayLast4part1(): Int =
    this.fold(0) { acc, line ->
        val pairs =
            line
                .split(",")
                .map { it.toRange() }

        val first = pairs.first()
        val second = pairs.last()

        when {
            first.first <= second.first && first.second >= second.second -> acc + 1
            second.first <= first.first && second.second >= first.second -> acc + 1
            else -> acc
        }
    }

suspend fun Flow<String>.y2023dayLast4part2(): Int =
    this.fold(0) { acc, line ->
        val pairs =
            line
                .split(",")
                .map { it.toRange() }

        val first = pairs.first()
        val second = pairs.last()

        when {
            first.first <= second.second && first.second >= second.first -> acc + 1
//            second.first <= first.first && second.second >= first.second -> acc + 1
            else -> acc
        }
    }
