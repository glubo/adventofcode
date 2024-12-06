package cz.glubo.adventofcode.y2023.day16

import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.Direction.DOWN
import cz.glubo.adventofcode.utils.Direction.LEFT
import cz.glubo.adventofcode.utils.Direction.RIGHT
import cz.glubo.adventofcode.utils.Direction.UP
import cz.glubo.adventofcode.utils.Field
import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.Orientation
import cz.glubo.adventofcode.utils.Orientation.HORIZONTAL
import cz.glubo.adventofcode.utils.Orientation.VERTICAL
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.Flow
import kotlin.math.max

private val logger = noCoLogger({}.javaClass.`package`.toString())

enum class Tile {
    NONE,
    MIRROR,
    MIRROR_BACK,
    VERTICAL_SPLIT,
    HORIZONTAL_SPLIT,
}

suspend fun Flow<String>.y2023day16part1(): Long {
    val cast = IVec2(-1, 0) to RIGHT
    val tileField = parseField()

    return countLavaTiles(tileField, cast)
}

suspend fun Flow<String>.y2023day16part2(): Long {
    val tileField = parseField()
    val maxX =
        (0..<tileField.width)
            .flatMap { x ->
                listOf(
                    countLavaTiles(tileField, IVec2(x, -1) to DOWN),
                    countLavaTiles(tileField, IVec2(x, tileField.height) to UP),
                )
            }.max()
    val maxY =
        (0..<tileField.height)
            .flatMap { y ->
                listOf(
                    countLavaTiles(tileField, IVec2(-1, y) to RIGHT),
                    countLavaTiles(tileField, IVec2(tileField.width, y) to LEFT),
                )
            }.max()
    return max(maxX, maxY)
}

private fun countLavaTiles(
    tileField: Field<Tile>,
    cast: Pair<IVec2, Direction>,
): Long {
    var height = tileField.height
    var width = tileField.width

    val lavaField =
        Field(
            width = width,
            height = height,
            fields = MutableList(width * height) { false },
        )

    val visited = mutableListOf<Pair<IVec2, Direction>>()

    val toCast = mutableListOf(cast)

    while (toCast.isNotEmpty()) {
        val (castFrom, castDirection) = toCast.removeFirst()
        visited.add(castFrom to castDirection)
        var rayPos = castFrom + castDirection.vector
        do {
            lavaField[rayPos] = true
            val newRays =
                when (tileField[rayPos]) {
                    Tile.NONE -> emptyList()
                    Tile.MIRROR -> listOf(rayPos to mirror(castDirection))
                    Tile.MIRROR_BACK -> listOf(rayPos to mirrorBack(castDirection))
                    Tile.VERTICAL_SPLIT ->
                        when (Orientation.of(castDirection)) {
                            HORIZONTAL -> listOf(rayPos to UP, rayPos to DOWN)
                            VERTICAL -> emptyList()
                        }

                    Tile.HORIZONTAL_SPLIT ->
                        when (Orientation.of(castDirection)) {
                            HORIZONTAL -> emptyList()
                            VERTICAL -> listOf(rayPos to LEFT, rayPos to RIGHT)
                        }

                    null -> emptyList()
                }

            if (newRays.isNotEmpty()) {
                toCast.addAll(
                    newRays.filter {
                        !visited.contains(it)
                    },
                )
                break
            }
            rayPos += castDirection.vector
        } while (!tileField.outside(rayPos))
    }

    lavaField.debug { if (it) '#' else '.' }
    return lavaField.fields
        .count { it }
        .toLong()
}

private suspend fun Flow<String>.parseField(): Field<Tile> {
    val tiles = mutableListOf<Tile>()
    var height = 0
    var width = 0
    this.collect { line ->
        width = line.length
        height++
        tiles.addAll(
            line.map { char ->
                when (char) {
                    '/' -> Tile.MIRROR
                    '\\' -> Tile.MIRROR_BACK
                    '|' -> Tile.VERTICAL_SPLIT
                    '-' -> Tile.HORIZONTAL_SPLIT
                    else -> Tile.NONE
                }
            },
        )
    }

    val tileField =
        Field(
            width = width,
            height = height,
            fields = tiles,
        )
    return tileField
}

/**
 * /
 */
fun mirror(direction: Direction): Direction =
    when (direction) {
        UP -> RIGHT
        DOWN -> LEFT
        LEFT -> DOWN
        RIGHT -> UP
    }

/**
 * \
 */
fun mirrorBack(direction: Direction): Direction =
    when (direction) {
        UP -> LEFT
        DOWN -> RIGHT
        LEFT -> UP
        RIGHT -> DOWN
    }
