package cz.glubo.adventofcode.y2024.day6

import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.Field
import cz.glubo.adventofcode.utils.FieldWithFakeTile
import cz.glubo.adventofcode.utils.IVec2
import kotlinx.coroutines.flow.Flow

suspend fun y2024day6part1(input: Flow<String>): Long {
    var (field, startPos: IVec2?) = parseFieldAndFindStart(input)

    var currentPos = startPos
    var currentDirection = Direction.UP
    do {
        field[currentPos]!!.visited = true
        var nextDirection = currentDirection
        var nextPos = currentPos + currentDirection.vector
        while (field[nextPos]?.char == '#') {
            nextDirection =
                when (nextDirection) {
                    Direction.UP -> Direction.RIGHT
                    Direction.DOWN -> Direction.LEFT
                    Direction.LEFT -> Direction.UP
                    Direction.RIGHT -> Direction.DOWN
                }
            nextPos = currentPos + nextDirection.vector
        }
        currentPos = nextPos
        currentDirection = nextDirection
    } while (!field.outside(currentPos))

    return field.fields.count { it.visited }.toLong()
}

private suspend fun parseFieldAndFindStart(input: Flow<String>): Pair<Field<Tile>, IVec2> {
    var field = input.parseField()
    var startPos: IVec2? = null
    (0..<field.height).forEach { y ->
        (0..<field.width).forEach { x ->
            val pos = IVec2(x, y)
            if (field[pos]!!.char == '^') {
                startPos = pos
            }
        }
    }
    if (startPos == null) throw RuntimeException("Start not found")

    return Pair(field, startPos!!)
}

private data class Tile(
    var char: Char,
    var visited: Boolean,
    var directions: MutableList<Direction>,
)

private suspend fun Flow<String>.parseField(): Field<Tile> {
    val tiles = mutableListOf<Tile>()
    var height = 0
    var width = 0
    this.collect { line ->
        width = line.length
        height++
        tiles.addAll(
            line.map { char ->
                Tile(
                    char,
                    false,
                    mutableListOf(),
                )
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

suspend fun y2024day6part2(input: Flow<String>): Long {
    var field = input.parseField()
    var startPos: IVec2? = null
    val candidates = mutableListOf<IVec2>()
    (0..<field.height).forEach { y ->
        (0..<field.width).forEach { x ->
            val pos = IVec2(x, y)
            when (field[pos]!!.char) {
                '^' -> startPos = pos
                '.' -> candidates += pos
            }
        }
    }
    if (startPos == null) throw RuntimeException("Start not found")

    val result =
        candidates.count { obstaclePos ->
            val newField =
                FieldWithFakeTile(
                    field.width,
                    field.height,
                    field.fields,
                    obstaclePos,
                    Tile('#', false, mutableListOf()),
                )

            newField.fields.forEach {
                it.visited = false
                it.directions = mutableListOf()
            }

            var cycled = false
            var currentPos = startPos!!
            var currentDirection = Direction.UP
            do {
                newField[currentPos]!!.visited = true
                newField[currentPos]!!.directions.add(currentDirection)

                var nextDirection = currentDirection
                var nextPos = currentPos + currentDirection.vector
                var i = 0
                while (newField[nextPos]?.char == '#') {
                    nextDirection =
                        when (nextDirection) {
                            Direction.UP -> Direction.RIGHT
                            Direction.DOWN -> Direction.LEFT
                            Direction.LEFT -> Direction.UP
                            Direction.RIGHT -> Direction.DOWN
                        }
                    nextPos = currentPos + nextDirection.vector
                    i++
                    if (i > 4) throw RuntimeException("Trapped")
                }
                currentPos = nextPos
                currentDirection = nextDirection

                if (newField[currentPos]?.directions?.contains(currentDirection) == true) {
                    cycled = true
                    break
                }
            } while (!newField.outside(currentPos))

            cycled
        }

    return result.toLong()
}
