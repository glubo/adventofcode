package cz.glubo.adventofcode.y2024.day12

import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.Grid
import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger

val logger = noCoLogger({}.javaClass.toString())

data class Tile(
    val value: Char,
    var used: Boolean = false,
)

fun fillAndCountPricePart1(
    grid: Grid<Tile>,
    pos: IVec2,
): Long {
    val allConnectedTiles = flood(pos, grid)

    val area = allConnectedTiles.size
    val circumference =
        allConnectedTiles.sumOf { a ->
            Direction.entries.sumOf {
                if (allConnectedTiles.contains(a + it.vector)) 0L else 1L
            }
        }

    return area * circumference
}

private fun flood(
    start: IVec2,
    grid: Grid<Tile>,
): MutableList<IVec2> {
    val allConnectedTiles = mutableListOf<IVec2>()
    val heads = mutableListOf(start)

    while (heads.isNotEmpty()) {
        val head = heads.removeFirst()
        allConnectedTiles.add(head)
        val currentTile = grid[head]!!
        currentTile.used = true

        Direction.entries.forEach { dir ->
            val newPosition = head + dir.vector
            when {
                grid[newPosition] == null -> return@forEach
                grid[newPosition]!!.used == true -> return@forEach
                grid[newPosition]!!.value != currentTile.value -> return@forEach
                heads.contains(newPosition) -> return@forEach
                allConnectedTiles.contains(newPosition) -> return@forEach
                else -> heads.add(newPosition)
            }
        }
    }
    return allConnectedTiles
}

suspend fun y2024day12part1(input: Input): Long {
    logger.info("year 2024 day 12 part 1")
    val grid = input.grid { Tile(it) }
    var sum = 0L
    grid.allIVec2().forEach { pos ->
        if (grid[pos]?.used == false) {
            sum += fillAndCountPricePart1(grid, pos)
        }
    }
    return sum
}

fun fillAndCount2(
    grid: Grid<Tile>,
    start: IVec2,
): Long {
    val allConnectedTiles = flood(start, grid)

    allConnectedTiles.sortBy { it.x + it.y }
    val area = allConnectedTiles.size

    val perimeter =
        buildSet {
            allConnectedTiles.forEach { pos ->
                Direction.entries.forEach { dir ->
                    if ((pos + dir.vector) !in allConnectedTiles) add(dir to pos)
                }
            }
        }

    val corners =
        perimeter
            .filterNot { (dir, pos) ->
                (dir to pos + dir.turnRight().vector) in perimeter
            }.map { it.second }

    return area.toLong() * corners.size
}

suspend fun y2024day12part2(input: Input): Long {
    logger.info("year 2024 day 12 part 2")
    val grid = input.grid { Tile(it) }
    var sum = 0L
    grid.allIVec2().forEach { pos ->
        if (grid[pos]?.used == false) {
            sum += fillAndCount2(grid, pos)
        }
    }
    return sum
}
