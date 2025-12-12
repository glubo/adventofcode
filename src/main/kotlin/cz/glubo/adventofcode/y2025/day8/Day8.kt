package cz.glubo.adventofcode.y2025.day8

import cz.glubo.adventofcode.utils.IVec3
import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlin.math.roundToLong
import kotlin.math.sqrt

val logger = noCoLogger({}.javaClass.toString())

class UnionFind(
    size: Int,
) {
    val parents = MutableList(size) { it }

    fun findRoot(x: Int): Int {
        val parent = parents[x]
        return if (parents[parent] != parent) {
            findRoot(parent).also { parents[x] = it }
        } else {
            parent
        }
    }

    fun markUnion(
        x: Int,
        y: Int,
    ) {
        val rootX = findRoot(x)
        val rootY = findRoot(y)
        parents[rootX] = rootY
    }

    fun sizes(): List<Int> =
        parents
            .groupBy { findRoot(it) }
            .map { (_, it) -> it.size }
}

suspend fun y2025day8part1(
    input: Input,
    num: Int = 1000,
): Long {
    logger.info("year 2025 day 8 part 1")
    val points = mutableListOf<IVec3>()
    input.lineFlow().collect { line ->
        val (x, y, z) =
            line
                .split(',')
                .map { it.toInt() }
        points.add(IVec3(x, y, z))
    }

    data class Connection(
        val i: Int,
        val j: Int,
        val squareLength: Long,
    ) {
        fun length() = sqrt(squareLength.toDouble()).roundToLong()
    }

    val allConnections =
        points.indices.flatMap { i ->
            (i + 1..<points.size).map { j ->
                Connection(
                    i,
                    j,
                    points[i] squareDistance points[j],
                )
            }
        }
    val chosenConnections =
        allConnections
            .sortedBy { it.squareLength }
            .take(num)
    val union = UnionFind(points.size)
    chosenConnections.forEach { conn ->
        union.markUnion(conn.i, conn.j)
    }

    return union
        .sizes()
        .sortedByDescending { it }
        .take(3)
        .fold(1L) { acc, i -> acc * i }
}

suspend fun y2025day8part2(input: Input): Long {
    logger.info("year 2025 day 8 part 2")
    val points = mutableListOf<IVec3>()
    input.lineFlow().collect { line ->
        val (x, y, z) =
            line
                .split(',')
                .map { it.toInt() }
        points.add(IVec3(x, y, z))
    }

    data class Connection(
        val i: Int,
        val j: Int,
        val squareLength: Long,
    ) {
        fun length() = sqrt(squareLength.toDouble()).roundToLong()
    }

    val allConnections =
        points.indices
            .flatMap { i ->
                (i + 1..<points.size).map { j ->
                    Connection(
                        i,
                        j,
                        points[i] squareDistance points[j],
                    )
                }
            }.sortedBy { it.squareLength }
    val union = UnionFind(points.size)

    var lastConnection: Connection
    var i = 0
    do {
        val conn = allConnections[i]
        union.markUnion(conn.i, conn.j)
        lastConnection = conn
        i++
    } while (union.sizes().size > 1 && allConnections.indices.contains((i)))

    return points[lastConnection.i].x.toLong() * points[lastConnection.j].x.toLong()
}
