package cz.glubo.adventofcode.y2025.day2

import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.fold

val logger = noCoLogger({}.javaClass.toString())

suspend fun y2025day2part1(input: Input): Long {
    logger.info("year 2025 day 2 part 1")
    return input.lineFlow().fold(0L) { acc, line ->
        acc +
            line
                .split(',')
                .filterNot { it.isEmpty() }
                .flatMap {
                    val (min, max) = it.split('-').map { it.toLong() }
                    (min..max)
                }.sumOf { idIn ->
                    val id = idIn.toString()
                    when {
                        (id.length % 2) > 0 -> 0L
                        isSymmetricId(id) -> id.toLong()
                        else -> 0L
                    }.also {
                        if (it > 0) logger.info { "!!$it" }
                    }
                }
    }
}

fun isSymmetricId(id: String): Boolean = id.drop(id.length / 2) == id.dropLast(id.length / 2)

fun isRepeatedId(id: String): Boolean =
    (1..id.length / 2).any { len ->
        val parts = id.chunked(len)
        parts.all { it == parts.first() }
    }

suspend fun y2025day2part2(input: Input): Long {
    logger.info("year 2025 day 2 part 2")
    return input.lineFlow().fold(0L) { acc, line ->
        acc +
            line
                .split(',')
                .filterNot { it.isEmpty() }
                .flatMap {
                    val (min, max) = it.split('-').map { it.toLong() }
                    (min..max)
                }.sumOf { idIn ->
                    val id = idIn.toString()
                    (
                        if (isRepeatedId(id)) {
                            id.toLong()
                        } else {
                            0L
                        }
                    ).also {
                        if (it > 0) logger.info { "!!$it" }
                    }
                }
    }
}
