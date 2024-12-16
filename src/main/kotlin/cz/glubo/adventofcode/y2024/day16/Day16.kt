package cz.glubo.adventofcode.y2024.day16

import cz.glubo.SortedArrayList
import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.Grid
import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.input.Input
import cz.glubo.adventofcode.y2024.day16.Tile.EMPTY
import cz.glubo.adventofcode.y2024.day16.Tile.END
import cz.glubo.adventofcode.y2024.day16.Tile.START
import cz.glubo.adventofcode.y2024.day16.Tile.WALL
import io.klogging.noCoLogger

val logger = noCoLogger({}.javaClass.toString())

enum class Tile {
    START,
    END,
    WALL,
    EMPTY,
}

suspend fun y2024day16part1(input: Input): Long {
    logger.info("year 2024 day 16 part 1")
    val grid = grid(input)
    val startPos = grid.allIVec2().first { grid[it] == START }

    data class Head(
        val position: IVec2,
        val direction: Direction,
        val cost: Long,
    ) : Comparable<Head> {
        override fun compareTo(other: Head): Int = this.cost.compareTo(other.cost)
    }

    val heads = SortedArrayList<Head>()
    heads.add(
        Head(
            startPos,
            Direction.RIGHT,
            0,
        ),
    )

    val visited = mutableListOf<Head>()

    while (heads.iterator().hasNext()) {
        val head = heads.iterator().next()

        when (grid[head.position]) {
            END -> return head.cost
            WALL -> Unit
            START, EMPTY -> {
                val alreadyVisited = visited.firstOrNull { it.position == head.position && it.direction == head.direction }
                val bestCost = alreadyVisited?.cost ?: Long.MAX_VALUE
                if (bestCost > head.cost) {
                    alreadyVisited?.let { visited.remove(it) }

                    visited.add(head)

                    heads.add(
                        Head(
                            head.position + head.direction.vector,
                            head.direction,
                            head.cost + 1,
                        ),
                    )
                    heads.add(
                        Head(
                            head.position,
                            head.direction.turnRight(),
                            head.cost + 1000,
                        ),
                    )
                    heads.add(
                        Head(
                            head.position,
                            head.direction.turnLeft(),
                            head.cost + 1000,
                        ),
                    )
                }
            }

            null -> error("Escaped the maze")
        }

        heads.filter { it != head }
    }
    error("Did not reach the end")
}

suspend fun y2024day16part2(input: Input): Long {
    logger.info("year 2024 day 16 part 2")
    val grid = grid(input)
    val startPos = grid.allIVec2().first { grid[it] == START }

    data class Head(
        val position: IVec2,
        val direction: Direction,
        val cost: Long,
        val parents: MutableSet<Head>,
    ) : Comparable<Head> {
        override fun compareTo(other: Head): Int = this.cost.compareTo(other.cost)
    }

    val heads = SortedArrayList<Head>()
    heads.add(
        Head(
            startPos,
            Direction.RIGHT,
            0,
            mutableSetOf(),
        ),
    )

    val visited = mutableListOf<Head>()
    val routes = mutableListOf<Head>()
    var endCost: Long? = null

    while (heads.iterator().hasNext()) {
        val head = heads.iterator().next()
        heads.filter { it != head }

        if ((endCost ?: Long.MAX_VALUE) < head.cost) {
            continue
        }

        when (grid[head.position]) {
            WALL -> continue
            END -> {
                if (endCost == null) endCost = head.cost
                routes.add(head)
            }

            START, EMPTY -> {
                val alreadyVisited = visited.firstOrNull { it.position == head.position && it.direction == head.direction }
                if ((alreadyVisited?.cost ?: -1) == head.cost) {
                    logger.debug { "DUPL" }
                    alreadyVisited!!.parents.addAll(head.parents)
                }
                if ((alreadyVisited?.cost ?: Long.MAX_VALUE) > head.cost) {
                    logger.debug { "NEXT" }
                    alreadyVisited?.let { visited.remove(it) }

                    visited.add(head)

                    heads.add(
                        Head(
                            head.position + head.direction.vector,
                            head.direction,
                            head.cost + 1,
                            mutableSetOf(head),
                        ),
                    )
                    heads.add(
                        Head(
                            head.position,
                            head.direction.turnRight(),
                            head.cost + 1000,
                            mutableSetOf(head),
                        ),
                    )
                    heads.add(
                        Head(
                            head.position,
                            head.direction.turnLeft(),
                            head.cost + 1000,
                            mutableSetOf(head),
                        ),
                    )
                }
            }

            null -> error("Escaped the maze")
        }
    }

    val backtraceHeads = routes
    val backtraceVisited = mutableSetOf<IVec2>()
    while (backtraceHeads.isNotEmpty()) {
        val h = backtraceHeads.removeFirst()
        backtraceVisited += h.position
        backtraceHeads.addAll(h.parents)
    }

    grid.debug { it, pos ->
        when {
            pos in backtraceVisited -> 'O'
            it == WALL -> '#'
            it == EMPTY -> '.'
            it == START -> 'S'
            it == END -> 'E'
            else -> '?'
        }
    }
    return backtraceVisited.size
        .toLong()
}

private suspend fun grid(input: Input): Grid<Tile> {
    val grid =
        input.grid {
            when (it) {
                'S' -> START
                'E' -> END
                '#' -> WALL
                '.' -> EMPTY
                else -> error("Unexpected character $it")
            }
        }
    return grid
}
