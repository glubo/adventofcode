package cz.glubo.adventofcode.y2024.day10

import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.Grid
import cz.glubo.adventofcode.utils.GridWithFakeTile
import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.input.Input

fun propagate(
    grid: Grid<Int>,
    currentPos: IVec2,
    currentHeight: Int,
    successConsumer: (IVec2) -> Unit,
) {
    val nextHeight = currentHeight + 1
    GridWithFakeTile(grid.width, grid.height, grid.fields, currentPos, 11)
        .debug { it ->
            when (it) {
                -1 -> '.'
                11 -> '#'
                else -> it.toString().last()
            }
        }

    Direction.entries.forEach { dir ->
        val nextPosition = currentPos + dir.vector
        when {
            grid.outside(nextPosition) -> Unit
            grid[nextPosition] != nextHeight -> Unit
            nextHeight == 9 -> successConsumer(nextPosition)
            else -> propagate(grid, nextPosition, nextHeight, successConsumer)
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

    return starts
        .sumOf { start ->
            val tailSet = mutableSetOf<IVec2>()
            propagate(grid, start, 0) { tail ->
                tailSet.add(tail)
            }
            tailSet.size
        }.toLong()
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

    return starts
        .sumOf { start ->
            var count = 0L
            propagate(grid, start, 0) { tail ->
                count++
            }
            count
        }
}
