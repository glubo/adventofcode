package cz.glubo.adventofcode.y2024.day21

import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.Grid
import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.input.Input
import cz.glubo.adventofcode.y2024.day21.Tile.A
import cz.glubo.adventofcode.y2024.day21.Tile.DOWN
import cz.glubo.adventofcode.y2024.day21.Tile.LAVA
import cz.glubo.adventofcode.y2024.day21.Tile.LEFT
import cz.glubo.adventofcode.y2024.day21.Tile.N0
import cz.glubo.adventofcode.y2024.day21.Tile.N1
import cz.glubo.adventofcode.y2024.day21.Tile.N2
import cz.glubo.adventofcode.y2024.day21.Tile.N3
import cz.glubo.adventofcode.y2024.day21.Tile.N4
import cz.glubo.adventofcode.y2024.day21.Tile.N5
import cz.glubo.adventofcode.y2024.day21.Tile.N6
import cz.glubo.adventofcode.y2024.day21.Tile.N7
import cz.glubo.adventofcode.y2024.day21.Tile.N8
import cz.glubo.adventofcode.y2024.day21.Tile.N9
import cz.glubo.adventofcode.y2024.day21.Tile.RIGHT
import cz.glubo.adventofcode.y2024.day21.Tile.UP
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.toList
import java.util.PriorityQueue

val logger = noCoLogger({}.javaClass.toString())

enum class Tile(
    var char: Char,
) {
    UP('^'),
    DOWN('v'),
    LEFT('<'),
    RIGHT('>'),
    A('A'),
    N0('0'),
    N1('1'),
    N2('2'),
    N3('3'),
    N4('4'),
    N5('5'),
    N6('6'),
    N7('7'),
    N8('8'),
    N9('9'),
    LAVA('#'),
    ;

    companion object {
        fun fromChar(c: Char) = Tile.entries.first { it.char == c }
    }
}

data class Path(
    val moves: List<Direction>,
    val sum: IVec2,
) : Comparable<Path> {
    override fun compareTo(other: Path) = this.moves.size.compareTo(other.moves.size)

    fun add(direction: Direction) =
        Path(
            moves + direction,
            sum + direction.vector,
        )

    fun asString() =
        moves.joinToString("") {
            it.toCommandChar().toString()
        }
}

class Layer(
    val keyboard: Grid<Tile>,
    val lowerLayer: Layer?,
    val layerName: String = "layer",
) {
    private val pathCache = mutableMapOf<Pair<IVec2, IVec2>, List<Path>>()
    private val movesCache = mutableMapOf<String, List<String>>()
    private val shortestCache = mutableMapOf<String, Long>()

    fun getShortestLength(input: String): Long =
        shortestCache.getOrPut(input) {
            input
                .split("A")
                .dropLast(1)
                .sumOf { innerMovelet ->
                    val movelet = "${innerMovelet}A"
                    logger.debug { "$layerName movelet: $movelet" }

                    getMovesForInput(movelet).minOf { moves ->
                        lowerLayer?.getShortestLength(moves) ?: moves.length.toLong()
                    }
                }
        }

    fun getMovesForInput(input: String): List<String> =
        movesCache.getOrPut(input) {
            logger.debug { "$layerName hydrating: $input" }
            var position = getPosition(A)
            var outputs = listOf("")

            input.forEach { c ->
                var targetPos =
                    getPosition(
                        Tile.fromChar(c),
                    )

                val moves = shortestPaths(position, targetPos)
                outputs =
                    outputs.flatMap { o ->
                        moves
                            .map { path ->
                                o + path.asString()
                            }
                    }

                outputs = outputs.map { it + "A" }
                position = targetPos
            }
            logger.debug { "$layerName hydrated $input: $outputs" }
            outputs
        }

    private fun shortestPaths(
        position: IVec2,
        targetPos: IVec2,
    ): List<Path> =
        pathCache.getOrPut(position to targetPos) {
            val paths = PriorityQueue<Path>()

            paths.add(Path(listOf(), IVec2(0, 0)))

            val result = mutableListOf<Path>()

            fun shortestLengthSoFar() = (result.firstOrNull()?.moves?.size ?: Int.MAX_VALUE)
            do {
                val path = paths.poll()
                val headPos = position + path.sum
                val head = keyboard[headPos]
                when {
                    path.moves.size > shortestLengthSoFar() -> {
                        Unit
                    }

                    headPos == targetPos -> {
                        result.add(path)
                    }

                    head == null -> {
                        Unit
                    }

                    head == LAVA -> {
                        Unit
                    }

                    else -> {
                        paths.addAll(
                            Direction.entries.map {
                                path.add(it)
                            },
                        )
                    }
                }
            } while (paths.isNotEmpty())

            result
        }

    private fun getPosition(t: Tile) =
        keyboard.allIVec2().first {
            keyboard[it] == t
        }
}

private fun numericGrid(): Grid<Tile> {
    val grid =
        Grid(
            width = 3,
            height = 4,
            fields =
                buildList {
                    repeat(3 * 4) {
                        add(LAVA)
                    }
                }.toMutableList(),
        )
    grid[IVec2(0, 0)] = N7
    grid[IVec2(1, 0)] = N8
    grid[IVec2(2, 0)] = N9
    grid[IVec2(0, 1)] = N4
    grid[IVec2(1, 1)] = N5
    grid[IVec2(2, 1)] = N6
    grid[IVec2(0, 2)] = N1
    grid[IVec2(1, 2)] = N2
    grid[IVec2(2, 2)] = N3
    grid[IVec2(0, 3)] = LAVA
    grid[IVec2(1, 3)] = N0
    grid[IVec2(2, 3)] = A
    return grid
}

private fun arrowGrid(): Grid<Tile> {
    val grid =
        Grid<Tile>(
            width = 3,
            height = 2,
            fields =
                buildList {
                    repeat(3 * 2) {
                        add(LAVA)
                    }
                }.toMutableList(),
        )
    grid[IVec2(0, 0)] = LAVA
    grid[IVec2(1, 0)] = UP
    grid[IVec2(2, 0)] = A
    grid[IVec2(0, 1)] = LEFT
    grid[IVec2(1, 1)] = DOWN
    grid[IVec2(2, 1)] = RIGHT
    return grid
}

fun buildLayers(n: Int): Layer {
    var currentLayer: Layer? = null

    repeat(n) { i ->
        currentLayer =
            Layer(
                arrowGrid(),
                currentLayer,
                "arrows-$i",
            )
    }

    return Layer(numericGrid(), currentLayer, "numeric")
}

suspend fun y2024day21part1(input: Input): Long {
    logger.info("year 2024 day 21 part 1")
    val layers = buildLayers(2)
    return input
        .lineFlow()
        .toList()
        .sumOf { line ->
            getIntValue(line) * layers.getShortestLength(line)
        }
}

suspend fun y2024day21part2(input: Input): Long {
    logger.info("year 2024 day 21 part 2")
    val layers = buildLayers(25)
    return input
        .lineFlow()
        .toList()
        .sumOf { line ->
            getIntValue(line) * layers.getShortestLength(line)
        }
}

private fun getIntValue(line: String) =
    line
        .dropWhile { it == '0' }
        .removeSuffix("A")
        .toInt()
