package cz.glubo.adventofcode.y2025.day1

import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.fold
import kotlin.math.log

val logger = noCoLogger({}.javaClass.toString())

data class State(
    val sum: Int,
    val pos: Int,
)

suspend fun y2025day1part1(input: Input): Long {
    logger.info("year 2025 day 1 part 1")
    return input
        .lineFlow()
        .fold(State(0, 50)) { acc, value ->
            val mult =
                when (value[0]) {
                    'L' -> -1
                    'R' -> 1
                    else -> error("Unknown direction [$value[0]}")
                }
            val step = value.drop(1).toInt()
            val newPos = (acc.pos + step * mult) % 100
            State(
                acc.sum + if (newPos == 0) 1 else 0,
                newPos,
            )
        }.sum
        .toLong()
}

suspend fun y2025day1part2(input: Input): Long {
    logger.info("year 2025 day 1 part 2")
    var pos = 50
    var score = 0
    input
        .lineFlow()
        .collect { value ->
            val step =
                when (value[0]) {
                    'L' -> -1
                    'R' -> 1
                    else -> error("Unknown direction [$value[0]}")
                }
            val steps = value.drop(1).toInt()
            repeat(steps) {
                pos += step
                while (pos < 0) pos += 100
                while (pos > 99) pos -= 100
                if (pos == 0) score++
            }
            logger.info { "$pos $score" }
        }
    return score.toLong()
}
