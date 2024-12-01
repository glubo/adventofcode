package cz.glubo.adventofcode.y2023.day3

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.toList
import kotlin.math.abs

fun Char.isSymbol() = !this.isDigit() && this != '.'

fun checkForSymbol(
    strings: List<String>,
    position: Int,
) = strings.any {
    val checkChars =
        listOfNotNull(
            it.getOrNull(position - 1),
            it.getOrNull(position),
            it.getOrNull(position + 1),
        )
    checkChars.any { c -> c.isSymbol() }
}

suspend fun Flow<String>.y2023day3part1(): Int {
    val lines = this.toList()
    var acc = 0
    lines.indices.forEach { i ->
        var currentNumber: Int? = null
        var foundMatchingSymbol = false
        (0..<lines[i].length).forEach { j ->
            val currentChar = lines[i][j]
            when {
                currentChar.isDigit() -> {
                    currentNumber = (currentNumber ?: 0) * 10 + currentChar.digitToInt()
                    foundMatchingSymbol = foundMatchingSymbol ||
                        checkForSymbol(
                            listOfNotNull(
                                lines.getOrNull(i - 1),
                                lines.getOrNull(i + 1),
                            ),
                            j,
                        )
                }

                currentChar.isSymbol() -> {
                    if (currentNumber != null) {
                        acc += currentNumber!!
                        currentNumber = null
                    }
                    foundMatchingSymbol = true
                }

                else -> {
                    if (currentNumber != null && foundMatchingSymbol) {
                        acc += currentNumber!!
                    }
                    currentNumber = null
                    foundMatchingSymbol = false
                }
            }
        }

        if (currentNumber != null && foundMatchingSymbol) {
            acc += currentNumber!!
        }
        currentNumber = null
        foundMatchingSymbol = false
    }
    return acc
}

suspend fun Flow<String>.y2023day3part2(): Int {
    data class Pos(
        val x: Int,
        val y: Int,
    )

    data class Number(
        val startPos: Pos,
        val length: Int,
        val value: Int,
    )

    val gearPositions = ArrayDeque<Pos>()
    val numbers = ArrayDeque<Number>()

    this.collectIndexed { y, line ->
        var currentNumber: Number? = null
        line.indices.forEach { x ->
            val currentChar = line[x]
            when {
                currentChar.isDigit() -> {
                    currentNumber =
                        if (currentNumber == null) {
                            Number(
                                Pos(x, y),
                                1,
                                currentChar.digitToInt(),
                            )
                        } else {
                            Number(
                                currentNumber!!.startPos,
                                currentNumber!!.length + 1,
                                currentNumber!!.value * 10 + currentChar.digitToInt(),
                            )
                        }
                }

                currentChar == '*' -> {
                    gearPositions.add(Pos(x, y))
                    if (currentNumber != null) {
                        numbers.add(currentNumber!!)
                        currentNumber = null
                    }
                }

                else -> {
                    if (currentNumber != null) {
                        numbers.add(currentNumber!!)
                        currentNumber = null
                    }
                }
            }
        }
        if (currentNumber != null) {
            numbers.add(currentNumber!!)
            currentNumber = null
        }
    }

    return gearPositions.sumOf { gearPos ->
        val adjacentNumbers =
            numbers.filter { number ->
                abs(number.startPos.y - gearPos.y) <= 1 &&
                    (number.startPos.x - 2 < gearPos.x) &&
                    (number.startPos.x + number.length + 1 > gearPos.x)
            }

        if (adjacentNumbers.size == 2) {
            adjacentNumbers.first().value * adjacentNumbers.last().value
        } else {
            0
        }
    }
}
