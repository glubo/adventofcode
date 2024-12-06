package cz.glubo.adventofcode.y2023.day2

import cz.glubo.adventofcode.y2023.day2.Color.BLUE
import cz.glubo.adventofcode.y2023.day2.Color.GREEN
import cz.glubo.adventofcode.y2023.day2.Color.RED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningFold
import kotlin.math.max

suspend fun Flow<String>.y2023day2part1(): Int {
    val sum =
        getGameValues(this)
            .runningFold(0) { accumulator, value ->
                accumulator + value
            }.last()

    return sum
}

suspend fun Flow<String>.y2023day2part2(): Int {
    val sum =
        getGameValues2(this)
            .runningFold(0) { accumulator, value ->
                accumulator + value
            }.last()

    return sum
}

val dayRegex = "^Game (?<id>\\d+):".toRegex()
val colorRegex = "(?<count>\\d+) (?<color>red|green|blue)".toRegex()

enum class Color {
    RED,
    GREEN,
    BLUE,
}

fun String.asColor() =
    when (this) {
        "red" -> RED
        "green" -> GREEN
        "blue" -> BLUE
        else -> null
    }

fun getGameId(line: String) =
    dayRegex
        .find(line)
        ?.groups
        ?.get("id")
        ?.value
        ?.toInt()
        ?: 0

fun getMaxColorCounts(line: String): Map<Color, Int> =
    colorRegex
        .findAll(line)
        .fold(emptyMap<Color, Int>().toMutableMap()) { acc, matchResult ->
            val color = matchResult.groups["color"]!!.value.asColor()!!
            val count = matchResult.groups["count"]!!.value.toInt()
            acc[color] = max(count, acc[color] ?: 0)
            acc
        }

fun getGameValues(inputLines: Flow<String>): Flow<Int> =
    inputLines.map { line ->
        val gameId = getGameId(line)
        val maxColorCounts = getMaxColorCounts(line)

        if (maxColorCounts.entries.none {
                when (it.key) {
                    RED -> it.value > 12
                    GREEN -> it.value > 13
                    BLUE -> it.value > 14
                }
            }
        ) {
            gameId
        } else {
            0
        }
    }

fun getGameValues2(inputLines: Flow<String>): Flow<Int> =
    inputLines.map { line ->
        val maxColorCounts = getMaxColorCounts(line)

        maxColorCounts.entries.fold(1) { acc, entry ->
            acc * entry.value
        }
    }
