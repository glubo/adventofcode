package cz.glubo.adventofcode.y2024.day10

import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.Grid
import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.input.Input

fun propagate(
    grid: Grid<Int>,
    currentPos: IVec2,
    currentHeight: Int,
): Set<IVec2> {
    val nextHeight = currentHeight + 1
    println("pos $currentPos height $currentHeight")

    return Direction.entries.fold(emptySet()) { acc, dir ->
        val nextPosition = currentPos + dir.vector
        when {
            grid.outside(nextPosition) -> acc
            grid[nextPosition] != nextHeight -> acc
            nextHeight == 9 -> acc + setOf(nextPosition)
            else -> acc + propagate(grid, nextPosition, nextHeight)
        }
    }
}

suspend fun y2024day10part1(input: Input): Long {
    val grid =
        input.grid {
            if (it.isDigit()) it.digitToInt() else -1
        }

    val starts =
        grid.allIVec2().filter {
            grid[it] == 0
        }

    return starts.sumOf { start ->
        propagate(grid, start, 0)
            .also {
                println(it)
            }.size
            .toLong()
    }
}

suspend fun y2024day10part2(input: Input): Long {
    val grid =
        input.grid {
            if (it.isDigit()) it.digitToInt() else -1
        }

    val starts =
        grid.allIVec2().filter {
            grid[it] == 0
        }

    return starts.sumOf { start ->
        propagate2(grid, start, 0)
            .also {
                println(it)
            }
    }
}

fun propagate2(
    grid: Grid<Int>,
    currentPos: IVec2,
    currentHeight: Int,
): Long {
    val nextHeight = currentHeight + 1
    println("pos $currentPos height $currentHeight")

    return Direction.entries.sumOf { dir ->
        val nextPosition = currentPos + dir.vector
        when {
            grid.outside(nextPosition) -> 0
            grid[nextPosition] != nextHeight -> 0
            nextHeight == 9 -> 1
            else -> propagate2(grid, nextPosition, nextHeight)
        }
    }
}
