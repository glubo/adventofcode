package cz.glubo.adventofcode.y2024.day11

import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.first

val logger = noCoLogger({}.javaClass.toString())

suspend fun y2024day11part1(input: Input): Long {
    logger.info("year 2024 day 11 part 1")
    val stones = input.lineFlow().first().split(" ")

    return stones.sumOf {
        countStones(it.toLong(), 25)
    }
}

suspend fun y2024day11part2(input: Input): Long {
    logger.info("year 2024 day 11 part 2")
    val stones = input.lineFlow().first().split(" ")

    return stones.sumOf {
        countStones(it.toLong(), 75)
    }
}

val resultMap = mutableMapOf<Pair<Long, Int>, Long>()

fun countStones(
    stone: Long,
    remaining: Int,
): Long =
    resultMap.getOrPut(Pair(stone, remaining)) {
        when {
            remaining < 1 -> 1
            stone == 0L -> countStones(1L, remaining - 1)
            "$stone".length % 2 == 0 -> {
                splitLongIntoHalves(stone).sumOf {
                    countStones(it, remaining - 1)
                }
            }

            else -> countStones(stone * 2024, remaining - 1)
        }
    }

private fun splitLongIntoHalves(it: Long): List<Long> {
    val tmp = "$it"
    return listOf(
        tmp.take(tmp.length / 2).toLong(),
        tmp.takeLast(tmp.length / 2).toLong(),
    )
}
