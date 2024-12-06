package cz.glubo.adventofcode.y2023.day9

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.fold

suspend fun Flow<String>.y2023day9part1(): Int =
    this.fold(0) { acc, line ->
        val predictions = mutableListOf(line.split(" ").map { it.toInt() })
        while (predictions.last().any { it != 0 }) {
            val last = predictions.last()
            predictions +=
                (1..<last.size).map { i ->
                    last[i] - last[i - 1]
                }
        }
        val ret =
            predictions
                .reversed()
                .fold(0) { sum, it ->
                    sum + it.last()
                }
        acc + ret
    }

suspend fun Flow<String>.y2023day9part2(): Int =
    this.fold(0) { acc, line ->
        val predictions = mutableListOf(line.split(" ").map { it.toInt() })
        while (predictions.last().any { it != 0 }) {
            val last = predictions.last()
            predictions +=
                (1..<last.size).map { i ->
                    last[i] - last[i - 1]
                }
        }
        val ret =
            predictions
                .reversed()
                .fold(listOf(0)) { extrapolations, it ->
                    val last = extrapolations.last()
                    extrapolations + listOf(it.first() - last)
                }
        acc + ret.last()
    }
