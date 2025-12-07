package cz.glubo.adventofcode.y2025.day7

import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.fold

val logger = noCoLogger({}.javaClass.toString())

suspend fun y2025day7part1(input: Input): Long {
    logger.info("year 2025 day 7 part 1")
    val (lasers, splits) =
        input.lineFlow().fold(emptySet<Int>() to 0L) { (prevLasers, splits), line ->
            val (newLasers, newSplits) =
                prevLasers.fold(emptySet<Int>() to splits) { (nl: Set<Int>, ns: Long), laser: Int ->
                    val (nnl: Set<Int>, nns: Long) =
                        if (line[laser] == '^') {
                            setOf(laser - 1, laser + 1).filter { line.indices.contains(it) }.toSet() to 1L
                        } else {
                            setOf(laser) to 0L
                        }
                    (nl + nnl) to (ns + nns)
                }
            logger.debug { "$newLasers $newSplits" }
            (newLasers + line.indices.filter { line[it] == 'S' }) to newSplits
        }
    return splits
}

suspend fun y2025day7part2(input: Input): Long {
    logger.info("year 2025 day 7 part 2")
    val lasers =
        input.lineFlow().fold(emptyList<Long>()) { prevLasers, line ->
            logger.debug { "$prevLasers " }
            val range = (0..<line.length)
            val nextLasers = MutableList(line.length) { 0L }

            line.forEachIndexed { pos, char ->
                when (char) {
                    'S' -> {
                        nextLasers[pos] += 1
                    }

                    '.' -> {
                        nextLasers[pos] += prevLasers.getOrNull(pos) ?: 0L
                    }

                    '^' -> {
                        if (range.contains(pos - 1)) {
                            nextLasers[pos - 1] += prevLasers.getOrNull(pos) ?: 0L
                        }
                        if (range.contains(pos + 1)) {
                            nextLasers[pos + 1] += prevLasers.getOrNull(pos) ?: 0L
                        }
                    }
                }
            }
            nextLasers
        }
    return lasers.sum()
}
