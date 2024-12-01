package cz.glubo.adventofcode.y2023.day17

import cz.glubo.adventofcode.utils.Field
import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.Orientation
import cz.glubo.adventofcode.utils.Orientation.HORIZONTAL
import cz.glubo.adventofcode.utils.Orientation.VERTICAL
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.Flow
import java.util.PriorityQueue

private val logger = noCoLogger({}.javaClass.`package`.toString())

data class HeatField(
    val hWidth: Int,
    val hHeight: Int,
    val hFields: MutableList<Int>,
) : Field<Int>(hWidth, hHeight, hFields) {
    fun debug() {
        logger.debug {
            "\n" + fields.chunked(width).map { it.joinToString("") { i -> "$i" } }.joinToString("\n")
        }
    }

    /**
     * lower = better
     */
    fun fitness(v: IVec2) = v.tcDistance(bottomRight)

    fun weightAcross(
        minSteps: Int,
        maxSteps: Int,
    ): Long {
        val visitsMap =
            Field<MutableMap<Orientation, Int>>(
                width = width,
                height = height,
                fields = MutableList(height * width) { mutableMapOf() },
            )
        visitsMap[topLeft] =
            mutableMapOf(
                HORIZONTAL to 0,
                VERTICAL to 0,
            )
        val toExplore = PriorityQueue<Pair<IVec2, Orientation>> { a, b -> fitness(b.first) - fitness(a.first) }
        toExplore.addAll(
            listOf(
                topLeft to HORIZONTAL,
                topLeft to VERTICAL,
            ),
        )

        while (toExplore.isNotEmpty()) {
            val (head, headOrientation) = toExplore.poll()
            val headHeat = visitsMap[head]!![headOrientation]!!
            val newOrientation = headOrientation.switch()
            newOrientation.directions.forEach { newDirection ->
                val nextHeads =
                    (minSteps..maxSteps)
                        .map { it to head + newDirection.vector * it }
                        .filter {
                            !outside(it.second)
                        }
                nextHeads.forEach { (i, nextHead) ->
                    val nextHeat =
                        headHeat +
                            (1..i).sumOf {
                                this[head + newDirection.vector * it]!!
                            }
                    val visitedHeat = visitsMap[nextHead]!!.getOrDefault(newOrientation, Int.MAX_VALUE)
                    if (nextHeat < visitedHeat) {
                        visitsMap[nextHead]!![newOrientation] = nextHeat
                        toExplore.add(nextHead to newOrientation)
                    }
                }
            }
        }

        return visitsMap[bottomRight]!!.values.min().toLong()
    }
}

suspend fun Flow<String>.parseField(): HeatField {
    var width: Int? = null
    var height = 0
    val fields = mutableListOf<Int>()
    this.collect {
        width = it.length
        fields.addAll(
            it.map { c -> c.digitToInt() },
        )
        height++
    }
    return HeatField(
        width!!,
        height,
        fields,
    )
}

suspend fun Flow<String>.y2023day17part1(): Long {
    val field = this.parseField()
    field.debug()

    return field.weightAcross(1, 3)
}

suspend fun Flow<String>.y2023day17part2(): Long {
    val field = this.parseField()
    field.debug()

    return field.weightAcross(4, 10)
}
