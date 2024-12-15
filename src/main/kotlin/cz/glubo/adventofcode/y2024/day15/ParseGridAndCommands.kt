package cz.glubo.adventofcode.y2024.day15

import cz.glubo.adventofcode.utils.Grid
import cz.glubo.adventofcode.utils.input.Input
import cz.glubo.adventofcode.utils.input.TestInput
import kotlinx.coroutines.flow.toList

suspend fun parseGridAndCommands(input: Input): Pair<Grid<Tile>, List<String>> {
    val lines = input.lineFlow().toList()

    val gridLines = lines.takeWhile { it.isNotBlank() }
    val gridLine = gridLines.joinToString(System.lineSeparator())
    val grid =
        TestInput(gridLine)
            .grid {
                when (it) {
                    '#' -> Tile.WALL
                    'O' -> Tile.STONE
                    '@' -> Tile.PLAYER
                    '.' -> Tile.EMPTY
                    else -> error("Unexpected tile $it")
                }
            }
    grid.debug { it ->
        when (it) {
            Tile.WALL -> '#'
            Tile.STONE -> 'O'
            Tile.PLAYER -> '@'
            Tile.EMPTY -> '.'
        }
    }
    val commandLines = lines.drop(gridLines.size + 1)
    return Pair(grid, commandLines)
}
