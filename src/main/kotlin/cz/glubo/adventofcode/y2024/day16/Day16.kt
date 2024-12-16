package cz.glubo.adventofcode.y2024.day16

import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.Grid
import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.input.Input
import cz.glubo.adventofcode.y2024.day16.Tile.EMPTY
import cz.glubo.adventofcode.y2024.day16.Tile.END
import cz.glubo.adventofcode.y2024.day16.Tile.START
import cz.glubo.adventofcode.y2024.day16.Tile.WALL
import io.klogging.noCoLogger
import java.util.PriorityQueue

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

    val heads = PriorityQueue<Head>()
    heads.add(
        Head(
            startPos,
            Direction.RIGHT,
            0,
        ),
    )

    val visited = mutableListOf<Head>()

    while (heads.isNotEmpty()) {
        val head = heads.poll()

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
typealias HeadQueue = PriorityQueue<Head>

data class Head(
    val position: IVec2,
    val direction: Direction,
    val cost: Long,
    val parents: MutableSet<Head>,
) : Comparable<Head> {
    override fun compareTo(other: Head): Int = this.cost.compareTo(other.cost)
}

suspend fun y2024day16part2(input: Input): Long {
    logger.info("year 2024 day 16 part 2")
    val grid = grid(input)
    val startPos = grid.allIVec2().first { grid[it] == START }

    val heads = HeadQueue()
    heads.add(
        Head(
            startPos,
            Direction.RIGHT,
            0,
            mutableSetOf(),
        ),
    )

    val visited = mutableMapOf<Pair<IVec2, Direction>, Head>()
    val routes = mutableListOf<Head>()
    var endCost: Long? = null

    while (heads.iterator().hasNext()) {
        val head = heads.poll()

        if ((endCost ?: Long.MAX_VALUE) < head.cost) {
            continue
        }

        fun HeadQueue.addIfViable(head: Head) {
            when {
                WALL == grid[head.position] -> Unit
                else -> this.add(head)
            }
        }

        when (grid[head.position]) {
            END -> {
                if (endCost == null) endCost = head.cost
                routes.add(head)
            }

            START, EMPTY -> {
                val alreadyVisited = visited.get(head.position to head.direction)
                if ((alreadyVisited?.cost ?: -1) == head.cost) {
                    logger.debug { "DUPL" }
                    alreadyVisited!!.parents.addAll(head.parents)
                }
                if ((alreadyVisited?.cost ?: Long.MAX_VALUE) > head.cost) {
                    logger.debug { "NEXT" }

                    visited[head.position to head.direction] = head

                    heads.addIfViable(
                        Head(
                            head.position + head.direction.vector,
                            head.direction,
                            head.cost + 1,
                            mutableSetOf(head),
                        ),
                    )
                    val turnRight = head.direction.turnRight()
                    heads.addIfViable(
                        Head(
                            head.position + turnRight.vector,
                            turnRight,
                            head.cost + 1001,
                            mutableSetOf(head),
                        ),
                    )
                    val turnLeft = head.direction.turnLeft()
                    heads.addIfViable(
                        Head(
                            head.position + turnLeft.vector,
                            turnLeft,
                            head.cost + 1001,
                            mutableSetOf(head),
                        ),
                    )
                }
            }

            else -> error("Escaped the maze")
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
