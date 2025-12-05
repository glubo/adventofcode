package cz.glubo.adventofcode.y2025.day4

import cz.glubo.adventofcode.utils.FullDirection
import cz.glubo.adventofcode.utils.Grid
import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger

val logger = noCoLogger({}.javaClass.toString())

suspend fun y2025day4part1(input: Input): Long {
    logger.info("year 2025 day 4 part 1")
    val grid =
        input.grid {
            when (it) {
                '@' -> true
                '.' -> false
                else -> error("unexpected char $it")
            }
        }
    return grid
        .allIVec2()
        .count { pos ->
            (grid[pos] ?: false) && grid.isMovable(pos)
        }.toLong()
}

fun Grid<Boolean>.isMovable(pos: IVec2): Boolean =
    FullDirection.entries.count { dir ->
        this[pos + dir.vector] ?: false
    } < 4

suspend fun y2025day4part2(input: Input): Long {
    logger.info("year 2025 day 4 part 2")
    var grid =
        input.grid {
            when (it) {
                '@' -> true
                '.' -> false
                else -> error("unexpected char $it")
            }
        }
    val startCount = grid.fields.count { it }
    do {
        var removed = false
        val nextGrid =
            grid.map { isRoll, pos ->
                when {
                    !isRoll -> false
                    grid.isMovable(pos) -> false.also { removed = true }
                    else -> true
                }
            }
        grid = nextGrid
    } while (removed)

    val endCount = grid.fields.count { it }
    return (startCount - endCount).toLong()
}
