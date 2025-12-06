package cz.glubo.adventofcode.y2025.day5

import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlin.math.max
import kotlin.math.min

val logger = noCoLogger({}.javaClass.toString())

private fun parseRange(input: String): LongRange {
    val (from, to) =
        input
            .split('-')
            .map { it.toLong() }
    return LongRange(from, to)
}

suspend fun y2025day5part1(input: Input): Long {
    logger.info("year 2025 day 5 part 1")
    val ranges = mutableListOf<LongRange>()
    val products = mutableListOf<Long>()
    var secondPart = false
    input.lineFlow().collect { line ->
        when {
            line.isBlank() -> secondPart = true
            !secondPart -> ranges.add(parseRange(line))
            secondPart -> products.add(line.toLong())
        }
    }

    return products
        .count { productId ->
            ranges.any { range -> range.contains(productId) }
        }.toLong()
}

suspend fun y2025day5part2(input: Input): Long {
    logger.info("year 2025 day 5 part 2")
    val ranges = LongRangeSet()
    var secondPart = false
    input.lineFlow().collect { line ->
        when {
            line.isBlank() -> secondPart = true
            !secondPart -> ranges.add(parseRange(line))
            secondPart -> Unit
        }
    }
    return ranges.countMembers()
}

fun LongRange.collides(other: LongRange) = this.start <= other.endInclusive && this.endInclusive >= other.start

class LongRangeSet {
    val ranges = mutableListOf<LongRange>()

    fun add(newRange: LongRange) {
        val collisions =
            ranges.indices.filter {
                ranges[it].collides(newRange)
            }
        if (collisions.isEmpty()) {
            val a = ranges.indexOfFirst { it.first > newRange.last }
            if (a > -1) {
                ranges.add(a, newRange)
            } else {
                ranges.add(newRange)
            }
        } else {
            val newFirst =
                min(
                    collisions.minOf { ranges[it].first },
                    newRange.first,
                )
            val newLast =
                max(
                    collisions.maxOf { ranges[it].last },
                    newRange.last,
                )
            val combinedRange = LongRange(newFirst, newLast)
            ranges[collisions.first()] = combinedRange
            collisions.drop(1).forEach { ranges.removeAt(it) }
        }
    }

    fun countMembers() = ranges.sumOf { it.last - it.first + 1 }
}
