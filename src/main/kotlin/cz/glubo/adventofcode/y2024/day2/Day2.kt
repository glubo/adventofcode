package cz.glubo.adventofcode.y2024.day2

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlin.math.abs

suspend fun y2024day2part1(input: Flow<String>): Long {
    val res =
        input.count { line ->
            val results =
                line
                    .split(" ")
                    .map { it.toInt() }

            part1(results)
        }
    return res.toLong()
}

private fun part1(results: List<Int>): Boolean {
    if (results.size < 2) return true

    val asc = results.first() < results[1]

    results.indices.drop(1).forEach { i ->
        val diff = results[i - 1] - results[i]
        if (isError(asc, diff)) {
            return false
        }
    }

    return true
}

suspend fun y2024day2part2(input: Flow<String>): Long {
    val res =
        input.count { line ->
            val results =
                line
                    .split(" ")
                    .map { it.toInt() }

            if (results.size < 2) return@count true

            val asc = results.first() < results[1]

            val firstError =
                results.indices.drop(1).firstOrNull { i ->
                    val diff = results[i - 1] - results[i]
                    isError(asc, diff)
                } ?: return@count true

            listOf(
                firstError,
                firstError - 1,
                0,
            ).forEach { toRemove ->
                val reducedResults =
                    results.toMutableList().apply {
                        removeAt(toRemove)
                    }
                if (part1(reducedResults)) {
                    return@count true
                }
            }
            return@count false
        }
    return res.toLong()
}

private fun isError(
    asc: Boolean,
    diff: Int,
) = when {
    asc != (diff < 0) -> true
    abs(diff) > 3 -> true
    abs(diff) < 1 -> true
    else -> false
}
