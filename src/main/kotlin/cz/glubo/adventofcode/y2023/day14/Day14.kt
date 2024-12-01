package cz.glubo.adventofcode.y2023.day14

import cz.glubo.adventofcode.y2023.day10.YXPair
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.toList

private val logger = noCoLogger({}.javaClass.`package`.toString())

fun computeLoad(
    currentStart: Int,
    currentStones: Int,
    height: Int,
): Int {
    val currentLoad =
        (currentStart..<currentStart + currentStones)
            .sumOf { height - it }

    return currentLoad
}

suspend fun Flow<String>.y2023day14part1(): Long {
    val inputLines = this.toList()
    val lines = inputLines + inputLines.first().map { '#' }.joinToString("")
    var load = 0L

    lines.first().indices.forEach { x ->
        var y = 0
        var currentStones = 0
        var currentStart = 0
        while (y in lines.indices) {
            when (lines[y][x]) {
                'O' -> currentStones++
                '.' -> {}
                '#' -> {
                    val currentLoad =
                        cz.glubo.adventofcode.y2023.day14.computeLoad(
                            currentStart,
                            currentStones,
                            inputLines.size,
                        )
                    cz.glubo.adventofcode.y2023.day14.logger.debug { "x: $x start: $currentStart currentLoad: $currentLoad stones: $currentStones" }
                    load += currentLoad
                    currentStart = y + 1
                    currentStones = 0
                }
            }
            y++
        }
    }
    return load
}

typealias XYPair = Pair<Int, Int>

fun cz.glubo.adventofcode.y2023.day14.XYPair.rotate(size: Int) = size - this.second - 1 to this.first

fun cz.glubo.adventofcode.y2023.day14.XYPair.order(size: Int) = this.first * size + this.second

fun List<YXPair>.myContains(
    needle: YXPair,
    size: Int,
): Boolean {
    val needleOrder = needle.order(size)
    return this.binarySearch { pair -> needleOrder.compareTo(pair.order(size)) } >= 0
}

data class Mirror(
    val size: Int,
    val rollingStones: List<cz.glubo.adventofcode.y2023.day14.XYPair>,
    val staticStones: List<cz.glubo.adventofcode.y2023.day14.XYPair>,
) {
    fun tiltUp(): cz.glubo.adventofcode.y2023.day14.Mirror {
        val newStones =
            rollingStones.sortedBy {
                it.order(size)
            }.toMutableList()

        newStones.indices.forEach { i ->
            val stone = newStones[i]
            var newY = stone.second
            while (newY > 0) {
                if (newStones.contains(stone.first to newY - 1)) break
                if (staticStones.contains(stone.first to newY - 1)) break
                newY--
            }
            newStones[i] = stone.first to newY
        }

        return cz.glubo.adventofcode.y2023.day14.Mirror(
            this.size,
            newStones,
            this.staticStones,
        )
    }

    fun weight() =
        this.rollingStones.sumOf {
            size - it.second
        }

    fun rotate() =
        cz.glubo.adventofcode.y2023.day14.Mirror(
            size,
            rollingStones.map { it.rotate(size) },
            staticStones.map { it.rotate(size) },
        )
}

suspend fun Flow<String>.toMirror(): cz.glubo.adventofcode.y2023.day14.Mirror {
    val static = mutableListOf<cz.glubo.adventofcode.y2023.day14.XYPair>()
    val rolling = mutableListOf<cz.glubo.adventofcode.y2023.day14.XYPair>()
    var size: Int? = null

    this.collectIndexed { y, line ->
        size = line.length
        line.forEachIndexed { x, c ->
            when (c) {
                '.' -> {}
                'O' -> rolling.add(x to y)
                '#' -> static.add(x to y)
                else -> throw RuntimeException("Unknown character at [$x, $y]: '$c'")
            }
        }
    }
    return cz.glubo.adventofcode.y2023.day14.Mirror(
        size = size!!,
        rollingStones = rolling.sortedBy { it.order(size!!) },
        staticStones = static.sortedBy { it.order(size!!) },
    )
}

fun cycle(input: cz.glubo.adventofcode.y2023.day14.Mirror) =
    (0..3).fold(input) { it, _ ->
        it.tiltUp()
            .rotate()
    }

suspend fun Flow<String>.y2023day14part2(): Long {
    val mirror = this.toMirror()

    var cycleStart: Int? = null
    var cycleEnd: Int? = null

    val map =
        buildMap<cz.glubo.adventofcode.y2023.day14.Mirror, Pair<Int, cz.glubo.adventofcode.y2023.day14.Mirror>> {
            var i = 0
            var m = mirror
            while (i < 1000000000) {
                if (i % 10000 == 0) cz.glubo.adventofcode.y2023.day14.logger.debug { i.toString() }
                val next = cz.glubo.adventofcode.y2023.day14.cycle(m)
                if (contains(next)) {
                    cycleEnd = i
                    cycleStart = get(next)!!.first
                    break
                } else {
                    put(m, i to next)
                    m = next
                }
                i++
            }
        }
    cz.glubo.adventofcode.y2023.day14.logger.debug { "Found a cycle $cycleStart to $cycleEnd" }
    cz.glubo.adventofcode.y2023.day14.logger.debug { map.values.map { it.first to it.second.weight() }.joinToString { "${it.first}: ${it.second}" } }
    val cycleLength: Int = cycleEnd!! - cycleStart!! + 1
    val remainingCycles: Int = (999999999 - cycleStart!!) % cycleLength
    val finalMirror: cz.glubo.adventofcode.y2023.day14.Mirror = map.values.first { it.first == cycleStart!! + remainingCycles }.second

    return finalMirror.weight().toLong()
}
