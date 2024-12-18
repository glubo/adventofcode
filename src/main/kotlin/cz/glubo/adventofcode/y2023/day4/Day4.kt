package cz.glubo.adventofcode.y2023.day4

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.fold

suspend fun Flow<String>.y2023day4part1(): Int =
    this.fold(0) { accumulator, line ->
        val firstSep = line.indexOf(':')
        val secondSep = line.indexOf('|')
        val winningNumbers =
            line
                .substring(firstSep + 1, secondSep)
                .split(' ')
                .filter { it.isNotBlank() }
                .map { it.toInt() }
                .toSet()
        val ourNumbers =
            line
                .substring(secondSep + 1, line.length)
                .split(' ')
                .filter { it.isNotBlank() }
                .map { it.toInt() }
        val ourWinningNumbers =
            ourNumbers
                .filter { winningNumbers.contains(it) }
        val value =
            if (ourWinningNumbers.isNotEmpty()) {
                pow2(ourWinningNumbers.size - 1)
            } else {
                0
            }
        println("our: $ourWinningNumbers value: $value")
        accumulator + value
    }

suspend fun Flow<String>.y2023day4part2(): Int {
    val copiesMap = emptyMap<Int, Int>().toMutableMap()
    var sum = 0

    this.collectIndexed { i, line ->
        val firstSep = line.indexOf(':')
        val secondSep = line.indexOf('|')
        val winningNumbers =
            line
                .substring(firstSep + 1, secondSep)
                .split(' ')
                .filter { it.isNotBlank() }
                .map { it.toInt() }
                .toSet()
        val ourNumbers =
            line
                .substring(secondSep + 1, line.length)
                .split(' ')
                .filter { it.isNotBlank() }
                .map { it.toInt() }
        val ourWinningNumbers =
            ourNumbers
                .filter { winningNumbers.contains(it) }

        val ourCopies = copiesMap[i] ?: 1
        val weGenerateCopies = ourWinningNumbers.size

        (1..weGenerateCopies).forEach { j ->
            copiesMap[i + j] = (copiesMap[i + j] ?: 1) + ourCopies
        }

        sum += ourCopies
    }
    println(copiesMap)
    return sum
}

fun pow2(n: Int): Int =
    if (n < 0) {
        throw IllegalArgumentException()
    } else if (n == 0) {
        1
    } else {
//        (0..<n).fold(1) { acc: Int, _: Int -> acc * 2 }
        1.shl(n)
    }
