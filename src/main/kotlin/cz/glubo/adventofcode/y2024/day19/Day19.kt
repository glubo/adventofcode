package cz.glubo.adventofcode.y2024.day19

import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.toList

val logger = noCoLogger({}.javaClass.toString())

fun isPossible(
    wanted: String,
    available: List<String>,
): Boolean {
    if (wanted == "") return true
    available.forEach {
        if (wanted.startsWith(it)) {
            if (isPossible(wanted.removePrefix(it), available)) return true
        }
    }
    return false
}

suspend fun y2024day19part1(input: Input): Long {
    logger.info("year 2024 day 19 part 1")

    val lines = input.lineFlow().toList()
    val available = lines.first().split(", ")
    val possible =
        lines
            .drop(2)
            .count { wanted ->
                isPossible(wanted, available)
            }
    return possible.toLong()
}

val cache = mutableMapOf<String, Long>()

fun sumPossible(
    wanted: String,
    available: List<String>,
): Long {
    if (wanted == "") return 1L

    return cache.getOrPut(wanted) {
        available.sumOf {
            if (wanted.startsWith(it)) {
                sumPossible(wanted.removePrefix(it), available)
            } else {
                0L
            }
        }
    }
}

suspend fun y2024day19part2(input: Input): Long {
    logger.info("year 2024 day 19 part 2")
    val lines = input.lineFlow().toList()
    val available = lines.first().split(", ")
    val possible =
        lines
            .drop(2)
            .sumOf { wanted ->
                val count = sumPossible(wanted, available)
                count
            }
    return possible
}
