package cz.glubo.adventofcode.day18

import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.Direction.DOWN
import cz.glubo.adventofcode.utils.Direction.UP
import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.Orientation
import cz.glubo.adventofcode.utils.Orientation.VERTICAL
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

suspend fun Flow<String>.day18part1(): Long {
    val ops = mutableListOf<Op>()
    this.collect { line ->
        val match = opRegex.find(line)
            ?: throw RuntimeException("Line $line does not match")
        ops.add(
            Op(
                direction = when (match.groups["opcode"]!!.value) {
                    "U" -> UP
                    "D" -> DOWN
                    "R" -> Direction.RIGHT
                    "L" -> Direction.LEFT
                    else -> throw RuntimeException("Invalid direction")
                },
                length = match.groups["length"]!!.value.toInt(),
                color = match.groups["color"]!!.value
            )
        )
    }

    return count(ops)
}

data class Line(
    val start: IVec2,
    val end: IVec2,
    val direction: Direction,
)

fun count(ops: List<Op>): Long {

    val lines = mutableListOf<Line>()
    ops.fold(IVec2(0, 0)) { pos, op ->
        val nextPos = pos + op.direction.vector * op.length
        if (Orientation.of(op.direction) == VERTICAL) {
            // we don't care about horizontal lines for ray tracing
            lines.add(
                Line(
                    start = pos,
                    end = nextPos,
                    direction = op.direction
                )
            )
        }
        nextPos
    }

    val verticalCuts = lines.map { it.start.y }.distinct().sorted()

    var lavaCount = 0L
    verticalCuts.forEachIndexed { index, y ->
        val topIntersects = lines.filter {
            when (it.direction) {
                UP -> y in (it.end.y + 1..it.start.y)
                DOWN -> y in (it.start.y + 1..it.end.y)
                else -> false
            }
        }.sortedBy { it.start.x }
        val topRanges = topIntersects.chunked(2)
            .map { (start, end) -> (start.start.x..end.start.x) }


        val botIntersects = lines.filter {
            when (it.direction) {
                UP -> y in (it.end.y..<it.start.y)
                DOWN -> y in (it.start.y..<it.end.y)
                else -> false
            }
        }.sortedBy { it.start.x }
        val botRanges = botIntersects.chunked(2)
            .map { (start, end) -> (start.start.x..end.start.x) }

        val topLavaCount =  (topRanges + botRanges).rangeUnion()
            .sumOf { it.endInclusive - it.start + 1 }
        val botMultiplier = if (verticalCuts.lastIndex != index) {
            verticalCuts[index + 1] - y - 1
        } else 1
        val botLavaCount = botRanges
            .sumOf { (it.endInclusive - it.start + 1).toLong() * botMultiplier }
        lavaCount += topLavaCount + botLavaCount
    }
    return lavaCount
}

suspend fun Flow<String>.day18part2(): Long {
    val ops = mutableListOf<Op>()
    this.collect { line ->
        val match = opRegex.find(line)
            ?: throw RuntimeException("Line $line does not match")
        val input = match.groups["color"]!!.value
        val newOp = Op(
            direction = when (input.last()) {
                '3' -> UP
                '1' -> DOWN
                '0' -> Direction.RIGHT
                '2' -> Direction.LEFT
                else -> throw RuntimeException("Invalid direction")
            },
            length = input.take(5).toInt(16),
            color = input
        )
        logger.debug { "$input: $newOp" }
        ops.add(
            newOp
        )
    }

    return count(ops)
}
