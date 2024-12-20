package cz.glubo.adventofcode.y2024.day20

import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.Grid
import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.input.Input
import cz.glubo.adventofcode.y2024.day20.TileType.EMPTY
import cz.glubo.adventofcode.y2024.day20.TileType.END
import cz.glubo.adventofcode.y2024.day20.TileType.START
import cz.glubo.adventofcode.y2024.day20.TileType.WALL
import io.klogging.noCoLogger

val logger = noCoLogger({}.javaClass.toString())

enum class TileType {
    WALL,
    EMPTY,
    START,
    END,
}

data class Tile(
    val type: TileType,
    var startDistance: Long?,
    var endDistance: Long?,
)

val possibleJumps =
    listOf(
        IVec2(-2, 0),
        IVec2(2, 0),
        IVec2(0, 2),
        IVec2(0, -2),
        IVec2(1, 1),
        IVec2(-1, 1),
        IVec2(-1, -1),
        IVec2(1, -1),
    )

suspend fun y2024day20part1(
    input: Input,
    atLeast: Int,
): Long {
    logger.info("year 2024 day 20 part 1")

    val grid =
        input.grid {
            when (it) {
                '#' -> Tile(WALL, null, null)
                '.' -> Tile(EMPTY, null, null)
                'S' -> Tile(START, null, null)
                'E' -> Tile(END, null, null)
                else -> error("unexpected tile $it")
            }
        }
    lateinit var startPos: IVec2
    lateinit var endPos: IVec2
    grid.allIVec2().forEach {
        when (grid[it]?.type) {
            START -> startPos = it
            END -> endPos = it
            else -> Unit
        }
    }

    floodStart(grid, startPos)
    floodEnd(grid, endPos)

    val normalLength = grid[startPos]!!.endDistance!!
    logger.debug { "Normal length: $normalLength" }
    return grid
        .allIVec2()
        .sumOf { pos ->
            val startDistance = grid[pos]?.startDistance ?: return@sumOf 0

            possibleJumps.count { jump ->
                val jumpPos = pos + jump
                val endDistanceJump = grid[jumpPos]?.endDistance ?: return@count false

                val saved = normalLength - 2 - startDistance - endDistanceJump
                if (pos.x == 7 && pos.y == 7) {
                    logger.debug { "$pos $jump $endDistanceJump $saved" }
                }
                if (saved >= atLeast) {
                    logger.info { "found jump $pos to $jumpPos saving $saved" }
                    true
                } else {
                    false
                }
            }
        }.toLong()
}

fun floodStart(
    grid: Grid<Tile>,
    startPos: IVec2,
) {
    val heads = mutableListOf(startPos)
    grid[startPos]!!.startDistance = 0
    while (heads.isNotEmpty()) {
        val head = heads.removeFirst()
        Direction.entries.forEach { dir ->
            val nextPos = head + dir.vector
            val nextTile = grid[nextPos] ?: return@forEach
            if (nextTile.type == WALL) return@forEach

            val currentDistance = grid[head]!!.startDistance!!
            val nextDistance = nextTile.startDistance ?: Long.MAX_VALUE
            if (nextDistance <= currentDistance + 1) {
                return@forEach
            }

            nextTile.startDistance = currentDistance + 1
            if (nextTile.type == EMPTY) {
                heads.add(nextPos)
            }
        }
    }
}

fun floodEnd(
    grid: Grid<Tile>,
    endPos: IVec2,
) {
    val heads = mutableListOf(endPos)
    grid[endPos]!!.endDistance = 0
    while (heads.isNotEmpty()) {
        val head = heads.removeFirst()
        Direction.entries.forEach { dir ->
            val nextPos = head + dir.vector
            val nextTile = grid[nextPos] ?: return@forEach
            if (nextTile.type == WALL) return@forEach

            val currentDistance = grid[head]!!.endDistance!!
            val nextDistance = nextTile.endDistance ?: Long.MAX_VALUE
            if (nextDistance <= currentDistance + 1) {
                return@forEach
            }

            nextTile.endDistance = currentDistance + 1
            if (nextTile.type == EMPTY) {
                heads.add(nextPos)
            }
        }
    }
}

suspend fun y2024day20part2(
    input: Input,
    atLeast: Int,
): Long {
    logger.info("year 2024 day 20 part 2")
    val grid =
        input.grid {
            when (it) {
                '#' -> Tile(WALL, null, null)
                '.' -> Tile(EMPTY, null, null)
                'S' -> Tile(START, null, null)
                'E' -> Tile(END, null, null)
                else -> error("unexpected tile $it")
            }
        }
    lateinit var startPos: IVec2
    lateinit var endPos: IVec2
    grid.allIVec2().forEach {
        when (grid[it]?.type) {
            START -> startPos = it
            END -> endPos = it
            else -> Unit
        }
    }

    floodStart(grid, startPos)
    floodEnd(grid, endPos)

    val possibleJumps2 = mutableMapOf<IVec2, Int>()
    possibleJumps2.put(IVec2(0, 0), 0)
    var cost = 0
    repeat(20) { _ ->
        val nextCost = cost + 1
        val heads = possibleJumps2.entries.filter { it.value == cost }
        heads.forEach { head ->
            Direction.entries.forEach { dir ->
                val nextPos = head.key + dir.vector
                if (possibleJumps2.get(nextPos) == null) possibleJumps2.put(nextPos, nextCost)
            }
        }
        cost = nextCost
    }

    val normalLength = grid[startPos]!!.endDistance!!
    logger.debug { "Normal length: $normalLength" }
    return grid
        .allIVec2()
        .sumOf { pos ->
            val startDistance = grid[pos]?.startDistance ?: return@sumOf 0

            possibleJumps2.count { jump ->
                val jumpPos = pos + jump.key
                val endDistanceJump = grid[jumpPos]?.endDistance ?: return@count false

                val saved = normalLength - jump.value - startDistance - endDistanceJump
                if (saved >= atLeast) {
                    logger.debug { "found jump $pos to $jumpPos saving $saved" }
                    true
                } else {
                    false
                }
            }
        }.toLong()
}
