package cz.glubo.adventofcode.y2024.day3

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.toList

suspend fun y2024day3part1(input: Flow<String>): Long {
    val regex = """mul\(([0-9]+),([0-9]+)\)""".toRegex()
    return input.fold(0L) { accu, line ->

        val matches = regex.findAll(line);
        val result = matches.fold(0L) { acc, it ->

            acc + it.groupValues[1].toLong() * it.groupValues[2].toLong()
        }
        accu + result
    }
}

suspend fun y2024day3part2(input: Flow<String>): Long {
    val regex = """mul\(([0-9]+),([0-9]+)\)""".toRegex()

    val dontRegex = """don't\(\).*?do\(\)""".toRegex()
    val line = input.toList()
        .joinToString(" ")
    val reducedLine = dontRegex.replace(line, "")

    val matches = regex.findAll(reducedLine);
    val result = matches.fold(0L) { acc, it ->

        acc + it.groupValues[1].toLong() * it.groupValues[2].toLong()
    }
    return result
}
