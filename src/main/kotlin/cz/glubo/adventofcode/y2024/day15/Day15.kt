package cz.glubo.adventofcode.y2024.day15

import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.Direction.DOWN
import cz.glubo.adventofcode.utils.Direction.LEFT
import cz.glubo.adventofcode.utils.Direction.RIGHT
import cz.glubo.adventofcode.utils.Direction.UP
import cz.glubo.adventofcode.utils.Grid
import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.input.Input
import cz.glubo.adventofcode.utils.input.TestInput
import cz.glubo.adventofcode.y2024.day15.Tile.EMPTY
import cz.glubo.adventofcode.y2024.day15.Tile.PLAYER
import cz.glubo.adventofcode.y2024.day15.Tile.STONE
import cz.glubo.adventofcode.y2024.day15.Tile.WALL
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.toList

val logger = noCoLogger({}.javaClass.toString())

enum class Tile {
    PLAYER,
    STONE,
    WALL,
    EMPTY,
}

private fun Grid<Tile>.countGPS(): Long =
    this.allIVec2().sumOf {
        when (this[it]) {
            STONE -> it.x + 100L * it.y
            else -> 0L
        }
    }

suspend fun y2024day15part1(input: Input): Long {
    logger.info("year 2024 day 15 part 1")
    val (grid, commandLines) = parseGridAndCommands(input)

    commandLines.forEach { line ->
        line.forEach { commandChar ->
            val direction =
                directionFromCommandChar(commandChar)
            move(grid, direction)
        }
    }

    return grid.countGPS()
}

private fun directionFromCommandChar(commandChar: Char) =
    when (commandChar) {
        '<' -> LEFT
        '>' -> RIGHT
        '^' -> UP
        'v' -> DOWN
        else -> error("unexpected direction $commandChar")
    }

private suspend fun parseGridAndCommands(input: Input): Pair<Grid<Tile>, List<String>> {
    val lines = input.lineFlow().toList()

    val gridLines = lines.takeWhile { it.isNotBlank() }
    val gridLine = gridLines.joinToString(System.lineSeparator())
    val grid =
        TestInput(gridLine)
            .grid {
                when (it) {
                    '#' -> WALL
                    'O' -> STONE
                    '@' -> PLAYER
                    '.' -> EMPTY
                    else -> error("Unexpected tile $it")
                }
            }
    grid.debug { it ->
        when (it) {
            WALL -> '#'
            STONE -> 'O'
            PLAYER -> '@'
            EMPTY -> '.'
        }
    }
    val commandLines = lines.drop(gridLines.size + 1)
    return Pair(grid, commandLines)
}

fun findPlayer(grid: Grid<Tile>) =
    grid.allIVec2().first {
        grid[it] == PLAYER
    }

fun move(
    grid: Grid<Tile>,
    direction: Direction,
) {
    val playerPos = findPlayer(grid)
    val stones: List<IVec2> =
        buildList {
            var currentPos = playerPos + direction.vector
            while (grid[currentPos] == STONE) {
                add(currentPos)
                currentPos += direction.vector
            }
        }
    val lastCart = stones.lastOrNull() ?: playerPos
    val canMove = grid[lastCart + direction.vector] == EMPTY
    if (canMove) {
        (stones.reversed() + listOf(playerPos)).forEach {
            grid[it + direction.vector] = grid[it]!!
        }
        grid[playerPos] = EMPTY
    }
}

data class Stone(
    var leftPos: IVec2,
) {
    fun both() = listOf(leftPos, leftPos + IVec2(1, 0))

    fun collides(pos: IVec2) = both().any { it == pos }
}

suspend fun y2024day15part2(input: Input): Long {
    logger.info("year 2024 day 15 part 2")

    val (grid, commandLines) = parseGridAndCommands(input)
    lateinit var playerPos: IVec2
    val stoneLefts = mutableListOf<Stone>()
    val walls = mutableListOf<IVec2>()
    grid.allIVec2().forEach {
        when (grid[it]) {
            PLAYER -> playerPos = IVec2(it.x * 2, it.y)
            STONE -> stoneLefts.add(Stone(IVec2(it.x * 2, it.y)))
            WALL -> {
                walls.add(IVec2(it.x * 2 + 1, it.y))
                walls.add(IVec2(it.x * 2, it.y))
            }

            else -> Unit
        }
    }
    commandLines.forEach { line ->
        line.forEach { commandChar ->
            val direction =
                directionFromCommandChar(commandChar)
            playerPos = move2(playerPos, stoneLefts, walls, direction)
            logger.debug {
                System.lineSeparator() +
                    (0..<grid.height).joinToString(System.lineSeparator()) { y ->
                        (0..<grid.width * 2)
                            .map { x ->
                                val pos = IVec2(x, y)

                                val rpos = pos + IVec2(-1, 0)
                                when {
                                    pos in walls -> '#'
                                    playerPos == pos -> '@'
                                    stoneLefts.any { it.leftPos == pos } -> '['
                                    stoneLefts.any { it.leftPos == rpos } -> ']'
                                    else -> '.'
                                }
                            }.joinToString("")
                    }
            }
        }
    }
    return stoneLefts.sumOf {
        it.leftPos.x + 100L * it.leftPos.y
    }
}

fun nextHead(
    pos: IVec2,
    direction: Direction,
) = when (direction) {
    UP, DOWN -> listOf(pos + direction.vector, pos + direction.vector + IVec2(-1, 0))
    LEFT, RIGHT -> listOf(pos + direction.vector)
}

fun move2(
    playerPos: IVec2,
    stones: MutableList<Stone>,
    walls: MutableList<IVec2>,
    direction: Direction,
): IVec2 {
    var heads = listOf(playerPos + direction.vector)
    val stoneTrain = mutableListOf<Stone>()
    while (heads.isNotEmpty()) {
        if (heads.any { it in walls }) return playerPos

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
                        UP, DOWN -> stone.both().map { it }
                        LEFT -> listOf(stone.leftPos)
                        RIGHT -> listOf(stone.leftPos + IVec2(1, 0))
                    }
                }.map { it + direction.vector }
    }

    logger.debug { "moving ${stoneTrain.size} stones ${direction.char}" }
    stoneTrain.forEach { it.leftPos += direction.vector }
    return playerPos + direction.vector
}
