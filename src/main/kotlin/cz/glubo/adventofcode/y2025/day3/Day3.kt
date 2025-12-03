package cz.glubo.adventofcode.y2025.day3

import cz.glubo.adventofcode.utils.input.Input
import cz.glubo.adventofcode.utils.pow10
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.fold

val logger = noCoLogger({}.javaClass.toString())

suspend fun y2025day3part1(input: Input): Long {
    logger.info("year 2025 day 3 part 1")
    return input.lineFlow().fold(0L) { acc, line ->
        acc + maxJolt(line, 1)
    }
}

suspend fun y2025day3part2(input: Input): Long {
    logger.info("year 2025 day 3 part 2")
    return input.lineFlow().fold(0L) { acc, line ->
        acc + maxJolt(line, 11)
    }
}

private fun maxJolt(
    line: String,
    remainingBatteries: Int,
): Long {
    val maxPos =
        (0..<line.length - remainingBatteries).maxBy {
            line[it].digitToInt()
        }
    val max = line[maxPos].digitToInt()

    return max.toLong() * pow10(remainingBatteries) +
        if (remainingBatteries > 0) {
            maxJolt(line.drop(maxPos + 1), remainingBatteries - 1)
        } else {
            0L
        }
}
