package cz.glubo.adventofcode.day10

import io.klogging.logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

private val logger = logger({}.javaClass.`package`.toString())

suspend fun Flow<String>.day10part1(): Long {
    val (tiles, startPosition) = this.parseTiles()

    return traceFenceAndMarkDistances(
        startPosition,
        tiles,
    )
}

suspend fun Flow<String>.day10part2(): Long {
    val (tiles, startPosition) = this.parseTiles()

    traceFenceAndMarkDistances(
        startPosition,
        tiles,
    )

    val cleanTiles = tiles.getCleanTiles(startPosition)

    cleanTiles.log()

    /*
        https://en.wikipedia.org/wiki/Point_in_polygon#Ray_casting_algorithm

        we ray trace top and bottom half of each line from left to right and when both halves of empty tile (not a fence) are inside (odd), the empty tile is inside

         ⥤      .┌┐..┌┐.┌┘..└┐.│.│
         TOP    000000000111222334
         BOTTOM 012223445555566778
         INSIDE ..........II....I.
     */

    val count =
        cleanTiles.sumOf { line ->
            var topParity = 0
            var botParity = 0
            var insideCount = 0L

            line.forEach { tile ->
                if (tile.connectedTo.isEmpty()) {
                    if (isInside(topParity, botParity)) insideCount++
                } else {
                    if (tile.connectedTo.contains(-1 to 0)) topParity++
                    if (tile.connectedTo.contains(1 to 0)) botParity++
                }
            }
            insideCount
        }

    return count
}

private data class Tile(
    val connectedTo: List<Pair<Int, Int>>,
    var distance: Long? = null,
)

private fun Tile.hasConnection(
    atPos: Pair<Int, Int>,
    toPos: Pair<Int, Int>,
) = this.connectedTo.any { (atPos.first + it.first == toPos.first) && (atPos.second + it.second == toPos.second) }

private fun List<List<Tile>>.getOrNull(position: Pair<Int, Int>) = this.getOrNull(position.first)?.getOrNull(position.second)

private fun traceFenceAndMarkDistances(
    startPosition: Pair<Int, Int>,
    tiles: List<List<Tile>>,
): Long {
    var heads = listOf(startPosition)
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

private fun List<List<Tile>>.getCleanTiles(startPosition: Pair<Int, Int>) =
    mapIndexed { y, line ->
        line.mapIndexed { x, dirtyTile ->
            when {
                y == startPosition.first && x == startPosition.second -> {
                    Tile(
                        connectedTo = dirtyTile.connectedTo.filter { getOrNull(y + it.first to x + it.second)?.distance != null },
                        distance = dirtyTile.distance,
                    )
                }

                dirtyTile.distance == null -> Tile(emptyList())
                else -> Tile(dirtyTile.connectedTo, dirtyTile.distance)
            }
        }
    }

private fun isInside(
    topParity: Int,
    botParity: Int,
): Boolean {
    return (topParity % 2 == 1) && (botParity % 2 == 1)
}

private suspend fun List<List<Tile>>.log() =
    this.forEach { line ->
        logger.info {
            line.map {
                if (it.connectedTo.isEmpty()) "." else "#"
            }.joinToString("")
        }
    }

private suspend fun Flow<String>.parseTiles(): Pair<List<List<Tile>>, Pair<Int, Int>> {
    var startPosition: Pair<Int, Int>? = null
    val tiles =
        toList().mapIndexed { i, line ->
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
    return tiles to startPosition!!
}
