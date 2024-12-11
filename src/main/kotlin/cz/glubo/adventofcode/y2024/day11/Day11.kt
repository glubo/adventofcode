package cz.glubo.adventofcode.y2024.day11

import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.first

val logger = noCoLogger({}.javaClass.toString())

suspend fun y2024day11part1(input: Input): Long {
    logger.info("year 2024 day 11 part 1")
    val stones = input.lineFlow().first().split(" ")

    return stones.sumOf {
//        evolve(it.toLong())
        countStones(it.toLong(), 25)
    }
}

fun evolve(start: Long): Long {
    var list = listOf(start)
    repeat(25) { _ ->
        val nextList =
            list.flatMap {
                when {
                    it == 0L -> listOf(1L)
                    "$it".length % 2 == 0 -> {
                        val tmp = "$it"
                        listOf(
                            tmp.take(tmp.length / 2).toLong(),
                            tmp.takeLast(tmp.length / 2).toLong(),
                        )
                    }

                    else -> listOf(it * 2024)
                }
            }
//        logger.debug(nextList)
        list = nextList
    }

    return list.size.toLong()
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

fun evolve2(start: Long): Long {
    var res = 0L
    res = countStones(start, 75)

    return res
}

private fun splitLongIntoHalves(it: Long): List<Long> {
    val tmp = "$it"
    return listOf(
        tmp.take(tmp.length / 2).toLong(),
        tmp.takeLast(tmp.length / 2).toLong(),
    )
}
