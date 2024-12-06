package cz.glubo.adventofcode.y2024.day4

import cz.glubo.adventofcode.utils.FullDirection
import cz.glubo.adventofcode.utils.Grid
import cz.glubo.adventofcode.utils.IVec2
import kotlinx.coroutines.flow.Flow

suspend fun y2024day4part1(input: Flow<String>): Long {
    data class Candidate1(
        var pos: IVec2,
        val remainingChars: String,
        val direction: FullDirection,
    )

    val field = input.parseGrid()
    val candidates = mutableListOf<Candidate1>()
    (0..<field.height).forEach { y ->
        (0..<field.width).forEach { x ->
            val position = IVec2(x, y)
            if (field[position] == 'X') {
                candidates.addAll(
                    FullDirection.entries.map { dir ->
                        Candidate1(
                            position,
                            "MAS",
                            dir,
                        )
                    },
                )
            }
        }
    }

    val res =
        candidates.count { candidate ->
            var remaining = candidate.remainingChars
            var pos = candidate.pos

            while (remaining.isNotEmpty()) {
                val nextPos = pos + candidate.direction.vector
                when {
                    field.outside(nextPos) -> return@count false
                    field[nextPos] != remaining.first() -> return@count false
                }
                remaining = remaining.drop(1)
                pos = nextPos
            }
            true
        }

    return res.toLong()
}

private suspend fun Flow<String>.parseGrid(): Grid<Char> {
    val tiles = mutableListOf<Char>()
    var height = 0
    var width = 0
    this.collect { line ->
        width = line.length
        height++
        tiles.addAll(
            line.map { char ->
                char
            },
        )
    }

    val tileGrid =
        Grid(
            width = width,
            height = height,
            fields = tiles,
        )
    return tileGrid
}

suspend fun y2024day4part2(input: Flow<String>): Long {
    val field = input.parseGrid()
    val candidates = mutableListOf<IVec2>()
    (0..<field.height).forEach { y ->
        (0..<field.width).forEach { x ->
            val position = IVec2(x, y)
            if (field[position] == 'A') {
                candidates.add(
                    position,
                )
            }
        }
    }

    val valid =
        listOf(
            listOf(
                Pair(FullDirection.UP_LEFT, 'M'),
                Pair(FullDirection.DOWN_RIGHT, 'S'),
                Pair(FullDirection.UP_RIGHT, 'S'),
                Pair(FullDirection.DOWN_LEFT, 'M'),
            ),
            listOf(
                Pair(FullDirection.UP_LEFT, 'S'),
                Pair(FullDirection.DOWN_RIGHT, 'M'),
                Pair(FullDirection.UP_RIGHT, 'S'),
                Pair(FullDirection.DOWN_LEFT, 'M'),
            ),
            listOf(
                Pair(FullDirection.UP_LEFT, 'S'),
                Pair(FullDirection.DOWN_RIGHT, 'M'),
                Pair(FullDirection.UP_RIGHT, 'M'),
                Pair(FullDirection.DOWN_LEFT, 'S'),
            ),
            listOf(
                Pair(FullDirection.UP_LEFT, 'M'),
                Pair(FullDirection.DOWN_RIGHT, 'S'),
                Pair(FullDirection.UP_RIGHT, 'M'),
                Pair(FullDirection.DOWN_LEFT, 'S'),
            ),
        )
    val res =
        candidates.count { candidate ->
            valid.forEach { list ->
                if (list.all {
                        field.get(candidate + it.first.vector) == it.second
                    }
                ) {
                    return@count true
                }
            }
            false
        }

    return res.toLong()
}
