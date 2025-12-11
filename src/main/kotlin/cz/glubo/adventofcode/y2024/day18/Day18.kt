package cz.glubo.adventofcode.y2024.day18

import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.Grid
import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.input.Input
import cz.glubo.adventofcode.utils.input.TestInput
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

val logger = noCoLogger({}.javaClass.toString())

suspend fun y2024day18part1(
    input: Input,
    i: Int,
    size: Int,
): Long {
    data class Tile(
        var corrupted: Boolean,
        var cost: Int,
    )

    val grid =
        Grid(
            size + 1,
            size + 1,
            (0..size)
                .flatMap {
                    (0..size).map {
                        Tile(
                            false,
                            Int.MAX_VALUE,
                        )
                    }
                }.toMutableList(),
        )

    input
        .lineFlow()
        .take(i)
        .collect { line ->
            val (x, y) =
                line
                    .split(",")
                    .map { it.toInt() }
            grid[IVec2(x, y)]!!.corrupted = true
        }

    val heads = mutableListOf(IVec2(0, 0))

    grid[IVec2(0, 0)]!!.cost = 0

    do {
        logger.debug { heads.size }
        val head = heads.removeFirst()
        val headTile = grid[head]!!
        Direction.entries.forEach { dir ->
            val nextPos = head + dir.vector
            val nextTile = grid[nextPos]
            when {
                nextTile == null -> {
                    Unit
                }

                nextTile?.corrupted == true -> {
                    Unit
                }

                nextTile?.cost ?: Int.MAX_VALUE <= headTile.cost + 1 -> {
                    Unit
                }

                else -> {
                    nextTile.cost = headTile.cost + 1
                    heads += nextPos
                }
            }
        }
    } while (heads.isNotEmpty())

    return grid[IVec2(size, size)]!!.cost.toLong()
}

suspend fun y2024day18part2(
    input: Input,
    minI: Int,
    size: Int,
): String {
    val lines = input.lineFlow().toList()

    var i = minI
    do {
        i++
        val result = y2024day18part1(TestInput(lines.asFlow()), i, size).toInt()
        if (result == Integer.MAX_VALUE) {
            return lines[i - 1]
        }
    } while (
        i <= lines.size
    )

    return "not found"
}
