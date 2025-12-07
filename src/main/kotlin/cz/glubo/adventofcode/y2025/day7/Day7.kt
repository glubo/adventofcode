package cz.glubo.adventofcode.y2025.day7

import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.fold
import kotlin.collections.getOrNull

val logger = noCoLogger({}.javaClass.toString())

suspend fun y2025day7part1(input: Input): Long {
    logger.info("year 2025 day 7 part 1")
    val (_, splits) =
        input.lineFlow().fold(emptySet<Int>() to 0L) { (prevBeams, splits), line ->
            val newBeams = mutableSetOf<Int>()
            var newSplits = splits
            prevBeams.forEach { pos: Int ->
                if (line[pos] == '^') {
                    if (line.indices.contains(pos - 1)) {
                        newBeams.add(pos - 1)
                    }
                    if (line.indices.contains(pos + 1)) {
                        newBeams.add(pos + 1)
                    }
                    newSplits++
                } else {
                    newBeams.add(pos)
                }
            }
            logger.debug { "$newBeams $newSplits" }
            (newBeams + line.indices.filter { line[it] == 'S' }) to newSplits
        }
    return splits
}

suspend fun y2025day7part2(input: Input): Long {
    logger.info("year 2025 day 7 part 2")
    val lasers =
        input.lineFlow().fold(emptyList<Long>()) { prevBeams, line ->
            logger.debug { "$prevBeams " }
            val nextBeams = MutableList(line.length) { 0L }

            line.forEachIndexed { pos, char ->
                when (char) {
                    'S' -> {
                        nextBeams[pos] += 1
                    }

                    '.' -> {
                        nextBeams[pos] += prevBeams.getOrNull(pos) ?: 0L
                    }

                    '^' -> {
                        if (line.indices.contains(pos - 1)) {
                            nextBeams[pos - 1] += prevBeams.getOrNull(pos) ?: 0L
                        }
                        if (line.indices.contains(pos + 1)) {
                            nextBeams[pos + 1] += prevBeams.getOrNull(pos) ?: 0L
                        }
                    }
                }
            }
            nextBeams
        }
    return lasers.sum()
}
