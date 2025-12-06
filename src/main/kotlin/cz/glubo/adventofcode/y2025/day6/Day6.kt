package cz.glubo.adventofcode.y2025.day6

import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

val logger = noCoLogger({}.javaClass.toString())

suspend fun y2025day6part1(input: Input): Long {
    logger.info("year 2025 day 6 part 1")
    val lines =
        input
            .lineFlow()
            .map {
                it
                    .split(" +".toRegex())
                    .filterNot { it.isBlank() }
            }.toList()
    val lastLine = lines.last()
    val problemLines = lines.dropLast(1)

    return problemLines.first().indices.sumOf { problem ->
        when (val op = lastLine[problem]) {
            "*" -> {
                problemLines.fold(1L) { acc, it ->
                    acc * it[problem].toLong()
                }
            }

            "+" -> {
                problemLines.fold(0L) { acc, it ->
                    acc + it[problem].toLong()
                }
            }

            else -> {
                error("Unknown operand $op")
            }
        }
    }
}

private enum class Op {
    ADD,
    MUL,
}

private fun readNumber(
    problemLines: List<String>,
    x: Int,
): Long =
    problemLines
        .map { it[x] }
        .filter { it.isDigit() }
        .fold(0L) { acc, it ->
            acc * 10 + it.digitToInt()
        }

suspend fun y2025day6part2(input: Input): Long {
    logger.info("year 2025 day 6 part 2")
    val lines =
        input
            .lineFlow()
            .toList()
    val lastLine = lines.last()
    val problemLines = lines.dropLast(1)

    data class Problem(
        val startX: Int,
        val endX: Int,
        val op: Op,
    )

    val problems = mutableListOf<Problem>()
    var prevX: Int? = null
    var prevOp: Op? = null
    lastLine.forEachIndexed { x, it ->
        val curOp =
            when (it) {
                '+' -> Op.ADD
                '*' -> Op.MUL
                else -> null
            }
        if (curOp != null) {
            if (prevOp != null && prevX != null) {
                problems.add(
                    Problem(
                        prevX,
                        x - 2,
                        prevOp,
                    ),
                )
            }
            prevX = x
            prevOp = curOp
        }
    }
    if (prevOp != null && prevX != null) {
        problems.add(
            Problem(
                prevX,
                lastLine.length - 1,
                prevOp,
            ),
        )
    }

    return problems.sumOf { problem ->
        when (problem.op) {
            Op.ADD -> {
                (problem.startX..problem.endX).sumOf { x ->
                    readNumber(problemLines, x)
                }
            }

            Op.MUL -> {
                (problem.startX..problem.endX).fold(1L) { acc, x ->
                    acc * readNumber(problemLines, x)
                }
            }
        }
    }
}
