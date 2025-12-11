package cz.glubo.adventofcode.y2024.day15

import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.Grid
import cz.glubo.adventofcode.utils.IVec2

data class World2(
    val size: IVec2,
    var playerPos: IVec2,
    val stones: MutableList<Stone>,
    val walls: MutableList<IVec2>,
) {
    fun debug() {
        logger.debug {
            System.lineSeparator() +
                (0..<size.y).joinToString(System.lineSeparator()) { y ->
                    (0..<size.x * 2)
                        .map { x ->
                            val pos = IVec2(x, y)

                            val rpos = pos + IVec2(-1, 0)
                            when {
                                pos in walls -> '#'
                                playerPos == pos -> '@'
                                stones.any { it.leftPos == pos } -> '['
                                stones.any { it.leftPos == rpos } -> ']'
                                else -> '.'
                            }
                        }.joinToString("")
                }
        }
    }

    fun move(direction: Direction) {
        var heads = listOf(playerPos + direction.vector)
        val stoneTrain = mutableListOf<Stone>()
        while (heads.isNotEmpty()) {
            // if the train hits the wall, cancel the movement
            if (heads.any { it in walls }) return

            val currentStones =
                stones.filter { stone ->
                    heads.any { head ->
                        stone.collides(head)
                    }
                }
            stoneTrain.addAll(currentStones)

            heads =
                currentStones
                    .flatMap { stone ->
                        when (direction) {
                            Direction.UP, Direction.DOWN -> stone.both().map { it }
                            Direction.LEFT -> listOf(stone.leftPos)
                            Direction.RIGHT -> listOf(stone.leftPos + IVec2(1, 0))
                        }
                    }.map { it + direction.vector }
        }

        logger.debug { "moving ${stoneTrain.size} stones ${direction.char}" }
        stoneTrain.forEach { it.leftPos += direction.vector }
        playerPos += direction.vector
    }

    fun countGPS() =
        stones.sumOf {
            it.leftPos.x + 100L * it.leftPos.y
        }

    data class Stone(
        var leftPos: IVec2,
    ) {
        fun both() = listOf(leftPos, leftPos + IVec2(1, 0))

        fun collides(pos: IVec2) = both().any { it == pos }
    }

    companion object {
        fun fromGrid(grid: Grid<Tile>): World2 {
            lateinit var playerPos: IVec2
            val stones = mutableListOf<Stone>()
            val walls = mutableListOf<IVec2>()
            grid.allIVec2().forEach {
                when (grid[it]) {
                    Tile.PLAYER -> {
                        playerPos = IVec2(it.x * 2, it.y)
                    }

                    Tile.STONE -> {
                        stones.add(Stone(IVec2(it.x * 2, it.y)))
                    }

                    Tile.WALL -> {
                        walls.add(IVec2(it.x * 2 + 1, it.y))
                        walls.add(IVec2(it.x * 2, it.y))
                    }

                    else -> {
                        Unit
                    }
                }
            }
            return World2(
                IVec2(grid.width, grid.height),
                playerPos,
                stones,
                walls,
            )
        }
    }
}
