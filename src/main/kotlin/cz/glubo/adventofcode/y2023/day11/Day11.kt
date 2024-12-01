package cz.glubo.adventofcode.y2023.day11

import io.klogging.logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private val logger = logger({}.javaClass.`package`.toString())

suspend fun Flow<String>.y2023day11part1(): Long {
    return sumOfDistances(2)
}

suspend fun Flow<String>.y2023day11part2(): Long {
    return sumOfDistances(1000000)
}

suspend fun Flow<String>.sumOfDistances(expansion: Int): Long {
    /** x to y */
    val galaxies = mutableListOf<Pair<Int, Int>>()
    val xUsed = mutableSetOf<Int>()
    val yUsed = mutableSetOf<Int>()
    var maxX = 0
    var maxY = 0

    this.collectIndexed { y, line ->
        line.forEachIndexed { x, character ->
            if (character == '#') {
                galaxies += x to y
                xUsed += x
                yUsed += y
                maxX = max(maxX, x)
                maxY = max(maxY, y)
            }
        }
    }

    logger.debug { "galaxies: $galaxies" }
    logger.debug { "xUsed: $xUsed" }
    logger.debug { "yUsed: $yUsed" }

    val gapsX = (0..maxX).filter { it !in xUsed }
    val gapsY = (0..maxY).filter { it !in yUsed }
    logger.debug { "gapsX: $gapsX" }
    logger.debug { "gapsY: $gapsY" }

    val expansionBonus = expansion - 1
    return galaxies.indices.sumOf { i ->
        (i + 1..<galaxies.size).sumOf { j ->
            val x1 = min(galaxies[i].first, galaxies[j].first)
            val x2 = max(galaxies[i].first, galaxies[j].first)
            val y1 = min(galaxies[i].second, galaxies[j].second)
            val y2 = max(galaxies[i].second, galaxies[j].second)

            val gapsXcrossed = (x1..x2).count { it in gapsX }
            val gapsYcrossed = (y1..y2).count { it in gapsY }

            val distance =
                (
                    0 +
                        abs(x1 - x2) +
                        abs(y1 - y2) +
                        gapsYcrossed * expansionBonus +
                        gapsXcrossed * expansionBonus
                ).toLong()
            logger.debug {
                "distance between $i $j: $distance; from ${galaxies[i]} to ${galaxies[j]}, gx: $gapsXcrossed gy: $gapsYcrossed "
            }
            distance
        }
    }
}
