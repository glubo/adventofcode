package cz.glubo.adventofcode.y2024.day13

import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.chunked
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map

val logger = noCoLogger({}.javaClass.toString())

data class LVec2(
    val x: Long,
    val y: Long,
)

data class Machine(
    val a: IVec2,
    val b: IVec2,
    val target: LVec2,
)

fun Input.parse(offset: Long): Flow<Machine> =
    this
        .lineFlow()
        .chunked(4)
        .map {
            var a = it[0].removePrefix("Button A: X+")
            val ax = a.takeWhile { it.isDigit() }.toInt()
            val ay = a.takeLastWhile { it.isDigit() }.toInt()

            var b = it[1].removePrefix("Button B: X+")
            val bx = b.takeWhile { it.isDigit() }.toInt()
            val by = b.takeLastWhile { it.isDigit() }.toInt()

            var p = it[2].removePrefix("Prize: X=")
            val px = p.takeWhile { it.isDigit() }.toLong() + offset
            val py = p.takeLastWhile { it.isDigit() }.toLong() + offset

            Machine(
                IVec2(ax, ay),
                IVec2(bx, by),
                LVec2(px, py),
            )
        }

data class Head(
    val price: Int,
    val depth: Int,
    val pos: IVec2,
)

fun Machine.minPrize(): Long {
    fun movesForB(): Long? {
        val i = target.y * a.x - target.x * a.y
        val j = a.x * b.y - a.y * b.x
        return if (i % j == 0L) i / j else null
    }

    fun movesForA(moveB: Long): Long? {
        val i = target.x - moveB * b.x
        val j = a.x
        return if (i % j == 0L) i / j else null
    }

    val b = movesForB()
    val a = b?.let { movesForA(it) }
    return if (a != null) a * 3 + b else 0L
}

suspend fun y2024day13part1(input: Input): Long {
    logger.info("year 2024 day 13 part 1")
    val machines = input.parse(0L)

    return machines.fold(0L) { acc, m ->
        acc + m.minPrize()
    }
}

suspend fun y2024day13part2(input: Input): Long {
    logger.info("year 2024 day 13 part 2")
    val machines = input.parse(10000000000000L)
    return machines.fold(0L) { acc, m ->
        acc + m.minPrize()
    }
}
