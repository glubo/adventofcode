package cz.glubo.adventofcode.y2023.day1

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningFold

suspend fun Flow<String>.y2023day1part1(): Int {
    val sum =
        parseCalibrationInput(this)
            .runningFold(0) { accumulator, value ->
                accumulator + value
            }.last()

    return sum
}

suspend fun Flow<String>.y2023day1part2(): Int {
    val sum =
        parseCalibrationInput(
            normaliseCalibrationInput(
                this,
            ),
        ).runningFold(0) { accumulator, value ->
            accumulator + value
        }.last()

    return sum
}

fun normaliseCalibrationInput(inputLines: Flow<String>): Flow<String> =
    inputLines.map { line ->
        line
            .replace("one", "one1one")
            .replace("two", "two2two")
            .replace("three", "three3three")
            .replace("four", "four4four")
            .replace("five", "five5five")
            .replace("six", "six6six")
            .replace("seven", "seven7seven")
            .replace("eight", "eight8eight")
            .replace("nine", "nine9nine")
    }

fun parseCalibrationInput(inputLines: Flow<String>): Flow<Int> =
    inputLines.map { line ->
        val first = line.first { it.isDigit() }.digitToInt()
        val last = line.last { it.isDigit() }.digitToInt()
        first * 10 + last
    }
