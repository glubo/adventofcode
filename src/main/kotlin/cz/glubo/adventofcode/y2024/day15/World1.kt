package cz.glubo.adventofcode.y2024.day15

import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.Grid
import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.y2024.day15.Tile.STONE

data class World1(
    var grid: Grid<Tile>,
) {
    fun findPlayer(grid: Grid<Tile>) =
        grid.allIVec2().first {
            grid[it] == Tile.PLAYER
        }

    fun move(
        grid: Grid<Tile>,
        direction: Direction,
    ) {
        val playerPos = findPlayer(grid)
        val stones: List<IVec2> =
            buildList {
                var currentPos = playerPos + direction.vector
                while (grid[currentPos] == Tile.STONE) {
                    add(currentPos)
                    currentPos += direction.vector
                }
            }
        val lastCart = stones.lastOrNull() ?: playerPos
        val canMove = grid[lastCart + direction.vector] == Tile.EMPTY
        if (canMove) {
            (stones.reversed() + listOf(playerPos)).forEach {
                grid[it + direction.vector] = grid[it]!!
            }
            grid[playerPos] = Tile.EMPTY
        }
    }

    fun countGPS(): Long =
        this.grid.allIVec2().sumOf {
            when (this.grid[it]) {
                STONE -> it.x + 100L * it.y
                else -> 0L
            }
        }
}
