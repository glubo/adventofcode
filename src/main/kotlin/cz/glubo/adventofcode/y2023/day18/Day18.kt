package cz.glubo.adventofcode.y2023.day18

import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.Direction.DOWN
import cz.glubo.adventofcode.utils.Direction.UP
import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.Orientation
import cz.glubo.adventofcode.utils.Orientation.VERTICAL
import cz.glubo.adventofcode.utils.length
import cz.glubo.adventofcode.utils.rangeUnion
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.Flow

private val logger = noCoLogger({}.javaClass.`package`.toString())

val opRegex = """(?<opcode>[UDRL]) (?<length>\d+) \(#(?<color>\p{XDigit}{6})\)""".toRegex()

data class Op(
    val direction: Direction,
    val length: Int,
    val color: String,
)

suspend fun Flow<String>.y2023day18part1(): Long {
    val ops = mutableListOf<Op>()
    this.collect { line ->
        val match =
            opRegex.find(line)
                ?: throw RuntimeException("Line $line does not match")
        ops.add(
            Op(
                direction =
                    when (match.groups["opcode"]!!.value) {
                        "U" -> UP
                        "D" -> DOWN
                        "R" -> Direction.RIGHT
                        "L" -> Direction.LEFT
                        else -> throw RuntimeException("Invalid direction")
                    },
                length = match.groups["length"]!!.value.toInt(),
                color = match.groups["color"]!!.value,
            ),
        )
    }

    return countLava(ops)
}

suspend fun Flow<String>.y2023day18part2(): Long {
    val ops = mutableListOf<Op>()
    this.collect { line ->
        val match =
            opRegex.find(line)
                ?: throw RuntimeException("Line $line does not match")
        val input = match.groups["color"]!!.value
        val newOp =
            Op(
                direction =
                    when (input.last()) {
                        '3' -> UP
                        '1' -> DOWN
                        '0' -> Direction.RIGHT
                        '2' -> Direction.LEFT
                        else -> throw RuntimeException("Invalid direction")
                    },
                length = input.take(5).toInt(16),
                color = input,
            )
        logger.debug { "$input: $newOp" }
        ops.add(
            newOp,
        )
    }

    return countLava(ops)
}

data class Line(
    val start: IVec2,
    val end: IVec2,
    val direction: Direction,
)

fun intersectToRanges(intersects: List<Int>): List<IntRange> =
    intersects
        .sorted()
        .chunked(2)
        .map { (start, end) -> (start..end) }

fun walkOps(
    ops: List<Op>,
    callback: (op: Op, pos: IVec2, nextPos: IVec2) -> Unit,
) = ops.fold(IVec2(0, 0)) { pos, op ->
    val nextPos = pos + op.direction.vector * op.length
    callback(op, pos, nextPos)
    nextPos
}

/**
 * https://en.wikipedia.org/wiki/Point_in_polygon#Ray_casting_algorithm
 *
 * we ray cast horizontally top and bottom from each horizontal cut (any vertical start/stop)
 * we then compute lava on current line (either top or bottom is inside)
 * and lava in between current and next cut (bottom is inside * inner distance to next)
 *
 */
fun countLava(ops: List<Op>): Long {
    val lines = mutableListOf<Line>()
    walkOps(ops) { op, pos, nextPos ->
        if (Orientation.of(op.direction) == VERTICAL) {
            // we don't care about horizontal lines for ray tracing similar to day 10
            lines.add(
                Line(
                    start = pos,
                    end = nextPos,
                    direction = op.direction,
                ),
            )
        }
    }

    val verticalCuts = lines.map { it.start.y }.distinct().sorted()

    var lavaCount =
        verticalCuts.foldIndexed(0L) { index, acc, y ->
            val topIntersects =
                lines.filter {
                    when (it.direction) {
                        UP -> y in (it.end.y + 1..it.start.y)
                        DOWN -> y in (it.start.y + 1..it.end.y)
                        else -> false
                    }
                }
            val topRanges = intersectToRanges(topIntersects.map { it.start.x })

            val botIntersects =
                lines
                    .filter {
                        when (it.direction) {
                            UP -> y in (it.end.y..<it.start.y)
                            DOWN -> y in (it.start.y..<it.end.y)
                            else -> false
                        }
                    }.sortedBy { it.start.x }
            val botRanges = intersectToRanges(botIntersects.map { it.start.x })

            val bothLavaCount =
                (topRanges + botRanges)
                    .rangeUnion()
                    .sumOf { it.length().toLong() }

            val botMultiplier =
                if (verticalCuts.lastIndex != index) {
                    verticalCuts[index + 1] - y - 1
                } else {
                    1
                }
            val botLavaCount = botRanges.sumOf { it.length().toLong() }

            acc + bothLavaCount + botLavaCount * botMultiplier
        }
    return lavaCount
}
