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

fun fillAndCount(
    grid: Grid<Tile>,
    pos: IVec2,
): Long {
    val all = mutableListOf<IVec2>()
    val heads = mutableListOf(pos)

    while (heads.isNotEmpty()) {
        val head = heads.removeFirst()
        all.add(head)
        val currentTile = grid[head]!!
        currentTile.used = true

        Direction.entries.forEach { dir ->
            val newPosition = head + dir.vector
            when {
                grid[newPosition] == null -> return@forEach
                grid[newPosition]!!.used == true -> return@forEach
                grid[newPosition]!!.value != currentTile.value -> return@forEach
                heads.contains(newPosition) -> return@forEach
                all.contains(newPosition) -> return@forEach
                else -> heads.add(newPosition)
            }
        }
    }
    val area = all.size
    val circumference =
        all.sumOf { a ->
            Direction.entries.sumOf {
                if (all.contains(a + it.vector)) 0L else 1L
            }
        }

    logger.debug {
        val a = grid[all.first()]?.value
        "$a $area * $circumference"
    }
    logger.debug { all }
    grid.debug { it ->
        if (it.used) it.value.uppercaseChar() else it.value.lowercaseChar()
    }
    return area * circumference
}

suspend fun y2024day12part1(input: Input): Long {
    logger.info("year 2024 day 12 part 1")
    val grid = input.grid { Tile(it) }
    var sum = 0L
    grid.allIVec2().forEach { pos ->
        if (grid[pos]?.used == false) {
            sum += fillAndCount(grid, pos)
        }
    }
    return sum
}

fun fillAndCount2(
    grid: Grid<Tile>,
    pos: IVec2,
): Long {
    val all = mutableListOf<IVec2>()
    val heads = mutableListOf(pos)

    while (heads.isNotEmpty()) {
        val head = heads.removeFirst()
        all.add(head)
        val currentTile = grid[head]!!
        currentTile.used = true

        Direction.entries.forEach { dir ->
            val newPosition = head + dir.vector
            when {
                grid[newPosition] == null -> return@forEach
                grid[newPosition]!!.used == true -> return@forEach
                grid[newPosition]!!.value != currentTile.value -> return@forEach
                heads.contains(newPosition) -> return@forEach
                all.contains(newPosition) -> return@forEach
                else -> heads.add(newPosition)
            }
        }
    }
    all.sortBy { it.x + it.y }
    val area = all.size
    val sides = mutableListOf<Pair<Direction, MutableList<IVec2>>>()
    all.forEach { currentPos ->
        Direction.entries.forEach { sideDirection ->
            if (all.contains(currentPos + sideDirection.vector)) return@forEach
            val possibleSameSides =
                sideDirection
                    .perpendicular()
                    .map { currentPos + it.vector }
                    .map { sideDirection to it }
            val sameSide =
                possibleSameSides
                    .map { pss ->
                        sides.firstOrNull { s ->
                            (pss.first == s.first) && s.second.any { pss.second == it }
                        }
                    }.firstOrNull { it !== null }

            if (sameSide != null) {
                sameSide.second.add(currentPos)
            } else {
                sides.add(sideDirection to mutableListOf(currentPos))
            }
        }
    }

    val circumference = sides.size

    return area.toLong() * circumference
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
