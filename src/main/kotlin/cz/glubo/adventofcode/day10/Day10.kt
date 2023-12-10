package cz.glubo.adventofcode.day10

import cz.glubo.adventofcode.day10.Color.FENCE
import cz.glubo.adventofcode.day10.Color.OUTSIDE
import io.klogging.logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

val logger = logger("asd")

suspend fun Flow<String>.day10part1(): Long {
    data class Tile(
        val connectedTo: List<Pair<Int, Int>>,
        var distance: Long? = null,
    )

    fun List<List<Tile>>.getOrNull(position: Pair<Int, Int>) = this.getOrNull(position.first)?.getOrNull(position.second)

    fun Tile.hasConnection(
        atPos: Pair<Int, Int>,
        toPos: Pair<Int, Int>,
    ) = this.connectedTo.any { (atPos.first + it.first == toPos.first) && (atPos.second + it.second == toPos.second) }

    val lines = this.toList()

    var startPosition: Pair<Int, Int>? = null
    val tiles =
        lines.mapIndexed { i, line ->
            line.mapIndexed { j, char ->
                when (char) {
                    '.' -> Tile(emptyList())
                    '-' -> Tile(listOf(0 to 1, 0 to -1))
                    '|' -> Tile(listOf(1 to 0, -1 to 0))
                    'J' -> Tile(listOf(-1 to 0, 0 to -1))
                    'L' -> Tile(listOf(-1 to 0, 0 to 1))
                    'F' -> Tile(listOf(1 to 0, 0 to 1))
                    '7' -> Tile(listOf(1 to 0, 0 to -1))
                    'S' -> {
                        startPosition = i to j
                        Tile(listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1), 0)
                    }

                    else -> throw RuntimeException("Unexpected tile at $i $j: '$char'")
                }
            }
        }
    if (startPosition == null) {
        throw RuntimeException("Start was not found")
    }

    var heads = listOf(startPosition!!)
    while (heads.isNotEmpty()) {
        val nextHeads = mutableListOf<Pair<Int, Int>>()
        heads.forEach { head ->
            val y = head.first
            val x = head.second
            val headTile = tiles[y][x]
            headTile.connectedTo.forEach { direction ->
                val nextPos = y + direction.first to x + direction.second
                val nextTile = tiles.getOrNull(nextPos)
                if (nextTile != null) {
                    when {
                        nextTile.distance == null -> {
                            if (nextTile.hasConnection(nextPos, head)) {
                                nextHeads.add(nextPos)
                                nextTile.distance = headTile.distance!! + 1
                            }
                        }

                        nextTile.distance!! <= headTile.distance!! - 1 -> {
                        }

                        nextTile.hasConnection(nextPos, head) -> {
                            return nextTile.distance!!
                        }
                    }
                }
            }
            heads = nextHeads
        }
    }

    return -2
}

enum class Color {
    INSIDE,
    OUTSIDE,
    FENCE,
}

suspend fun Flow<String>.day10part2(): Long {
    data class Tile(
        val connectedTo: List<Pair<Int, Int>>,
        var distance: Long? = null,
        var color: Color? = null,
    )

    fun <T> List<List<T>>.getOrNull(position: Pair<Int, Int>) = this.getOrNull(position.first)?.getOrNull(position.second)

    fun Tile.hasConnection(
        atPos: Pair<Int, Int>,
        toPos: Pair<Int, Int>,
    ) = this.connectedTo.any { (atPos.first + it.first == toPos.first) && (atPos.second + it.second == toPos.second) }

    val lines = this.toList()

    var startPosition: Pair<Int, Int>? = null
    val tiles =
        lines.mapIndexed { i, line ->
            line.mapIndexed { j, char ->
                when (char) {
                    '.' -> Tile(emptyList())
                    '-' -> Tile(listOf(0 to 1, 0 to -1))
                    '|' -> Tile(listOf(1 to 0, -1 to 0))
                    'J' -> Tile(listOf(-1 to 0, 0 to -1))
                    'L' -> Tile(listOf(-1 to 0, 0 to 1))
                    'F' -> Tile(listOf(1 to 0, 0 to 1))
                    '7' -> Tile(listOf(1 to 0, 0 to -1))
                    'S' -> {
                        startPosition = i to j
                        Tile(listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1), 0)
                    }

                    else -> throw RuntimeException("Unexpected tile at $i $j: '$char'")
                }
            }
        }
    if (startPosition == null) {
        throw RuntimeException("Start was not found")
    }

    var heads = listOf(startPosition!!)
    var shouldStop = false
    while (heads.isNotEmpty() && !shouldStop) {
        val nextHeads = mutableListOf<Pair<Int, Int>>()
        heads.forEach { head ->
            val y = head.first
            val x = head.second
            val headTile = tiles[y][x]
            headTile.connectedTo.forEach { direction ->
                val nextPos = y + direction.first to x + direction.second
                val nextTile = tiles.getOrNull(nextPos)
                if (nextTile != null) {
                    when {
                        nextTile.distance == null -> {
                            if (nextTile.hasConnection(nextPos, head)) {
                                nextHeads.add(nextPos)
                                nextTile.distance = headTile.distance!! + 1
                            }
                        }

                        nextTile.distance!! <= headTile.distance!! - 1 -> {
                        }

                        nextTile.hasConnection(nextPos, head) -> shouldStop = true
                    }
                }
            }
            heads = nextHeads
        }
    }

    val cleanTiles =
        tiles.mapIndexed { y, line ->
            line.mapIndexed { x, dirtyTile ->
                when {
                    y == startPosition!!.first && x == startPosition!!.second -> {
                        Tile(
                            connectedTo = dirtyTile.connectedTo.filter { tiles.getOrNull(y + it.first to x + it.second)?.distance != null },
                            distance = dirtyTile.distance,
                            color = FENCE,
                        )
                    }

                    dirtyTile.distance == null -> Tile(emptyList())
                    else -> Tile(dirtyTile.connectedTo, dirtyTile.distance, FENCE)
                }
            }
        }
    cleanTiles.forEach { line ->
        logger.info {
            line.map {
                if (it.connectedTo.isEmpty()) "." else "#"
            }.joinToString("")
        }
    }

    val detailColors: List<MutableList<Color?>> =
        (0..<cleanTiles.size * 2).map { y ->
            (0..<cleanTiles.first().size * 2).map { x ->
                null
            }.toMutableList()
        }

    var floodOutside =
        emptyList<Pair<Int, Int>>() +
            (0..<cleanTiles.size * 2).flatMap { y ->
                listOf(y to 0, y to cleanTiles.first().size * 2 - 1)
            } +
            (0..<cleanTiles.first().size * 2).flatMap { x ->
                listOf(0 to x, cleanTiles.size * 2 - 1 to x)
            }

    while (floodOutside.isNotEmpty()) {
        val nextFlood = mutableListOf<Pair<Int, Int>>()

        floodOutside.forEach { floodPos: Pair<Int, Int> ->
            if (detailColors.getOrNull(floodPos) == null) {
                val currentTile = cleanTiles.getOrNull(floodPos.first / 2 to floodPos.second / 2)!!
                val innerY = floodPos.first % 2
                val innerX = floodPos.second % 2
                detailColors[floodPos.first][floodPos.second] = OUTSIDE
                val newDirections =
                    when {
                        innerX == 0 && innerY == 0 -> {
                            // TOP-LEFT
                            listOfNotNull(
                                -1 to 0,
                                0 to -1,
                                if (currentTile.connectedTo.none { it.first == -1 && it.second == 0 }) 0 to 1 else null,
                                if (currentTile.connectedTo.none { it.first == 0 && it.second == -1 }) 1 to 0 else null,
                            )
                        }

                        innerX == 1 && innerY == 0 -> {
                            // TOP-RIGHT
                            listOfNotNull(
                                -1 to 0,
                                if (currentTile.connectedTo.none { it.first == -1 && it.second == 0 }) 0 to -1 else null,
                                0 to 1,
                                if (currentTile.connectedTo.none { it.first == 0 && it.second == 1 }) 1 to 0 else null,
                            )
                        }

                        innerX == 0 && innerY == 1 -> {
                            // BOT-LEFT
                            listOfNotNull(
                                if (currentTile.connectedTo.none { it.first == 0 && it.second == -1 }) -1 to 0 else null,
                                0 to -1,
                                if (currentTile.connectedTo.none { it.first == 1 && it.second == 0 }) 0 to 1 else null,
                                1 to 0,
                            )
                        }

                        innerX == 1 && innerY == 1 -> {
                            // BOT-RIGHT
                            listOfNotNull(
                                if (currentTile.connectedTo.none { it.first == 0 && it.second == 1 }) -1 to 0 else null,
                                if (currentTile.connectedTo.none { it.first == 1 && it.second == 0 }) 0 to -1 else null,
                                0 to 1,
                                1 to 0,
                            )
                        }

                        else -> throw RuntimeException("ugh")
                    }
                logger.debug { "at $floodPos newDirections: $newDirections" }
                nextFlood.addAll(
                    newDirections.map { floodPos.first + it.first to floodPos.second + it.second },
                )
            }
        }

        logger.debug { nextFlood }
        floodOutside =
            nextFlood.filter {
                it.first >= 0 &&
                    it.first < detailColors.size &&
                    it.second >= 0 &&
                    it.second < detailColors.first().size
            }
        logger.debug { floodOutside }
        detailColors.forEach { line ->
            logger.debug {
                line.map {
                    when (it) {
                        OUTSIDE -> 'O'
                        Color.INSIDE -> "I"
                        FENCE -> "F"
                        null -> "."
                    }
                }.joinToString("")
            }
        }
    }

    detailColors.forEach { line ->
        logger.debug {
            line.map {
                when (it) {
                    OUTSIDE -> 'O'
                    Color.INSIDE -> "I"
                    FENCE -> "F"
                    null -> "."
                }
            }.joinToString("")
        }
    }

    var count = 0L
    cleanTiles.indices.forEach { y ->
        cleanTiles.first().indices.forEach { x ->
            if (
                detailColors.getOrNull(y * 2 to x * 2) == null &&
                detailColors.getOrNull(y * 2 + 1 to x * 2) == null &&
                detailColors.getOrNull(y * 2 to x * 2 + 1) == null &&
                detailColors.getOrNull(y * 2 + 1 to x * 2 + 1) == null
            ) {
                count++
            }
        }
    }

    return count
}
