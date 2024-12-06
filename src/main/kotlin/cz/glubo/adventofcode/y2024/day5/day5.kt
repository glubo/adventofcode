package cz.glubo.adventofcode.y2024.day5

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

suspend fun y2024day5part1(input: Flow<String>): Long {
    val inputList = input.toList().toMutableList()
    val rules = mutableListOf<Pair<Int, Int>>()
    do {
        val line = inputList.removeFirst()
        if (line.isBlank()) continue

        val rule =
            line
                .split('|')
                .map { it.toInt() }
                .let { it[0] to it[1] }

        rules.add(rule)
    } while (line.isNotBlank())

    val result =
        inputList.sumOf { line ->

            val numbers =
                line
                    .split(',')
                    .map { it.toInt() }
            val positions =
                numbers
                    .mapIndexed { position, value ->
                        value to position
                    }.toMap()

            val valid =
                rules.all { rule ->
                    when {
                        positions[rule.first] == null -> true
                        positions[rule.second] == null -> true
                        else -> positions[rule.first]!! < positions[rule.second]!!
                    }
                }

            if (valid) {
                numbers[numbers.size / 2]
            } else {
                0
            }
        }

    return result.toLong()
}

suspend fun y2024day5part2(input: Flow<String>): Long {
    val inputList = input.toList().toMutableList()
    val rules = mutableListOf<Pair<Int, Int>>()
    do {
        val line = inputList.removeFirst()
        if (line.isBlank()) continue

        val rule =
            line
                .split('|')
                .map { it.toInt() }
                .let { it[0] to it[1] }

        rules.add(rule)
    } while (line.isNotBlank())

    val result =
        inputList.sumOf { line ->

            val numbers =
                line
                    .split(',')
                    .map { it.toInt() }
            val positions =
                numbers
                    .mapIndexed { position, value ->
                        value to position
                    }.toMap()
                    .toMutableMap()

            val valid =
                rules.all { rule ->
                    when {
                        positions[rule.first] == null -> true
                        positions[rule.second] == null -> true
                        else -> positions[rule.first]!! < positions[rule.second]!!
                    }
                }

            if (valid) return@sumOf 0

            val newNumbers =
                numbers.sortedWith { a, b ->
                    val matchingRule =
                        rules.firstOrNull {
                            (it.second == a && it.first == b) ||
                                (it.second == b && it.first == a)
                        }
                    when {
                        matchingRule == null -> 0
                        matchingRule.first == a && matchingRule.second == b -> 1
                        matchingRule.second == a && matchingRule.first == b -> -1
                        else -> throw RuntimeException("ads")
                    }
                }
            println(newNumbers)

            newNumbers[numbers.size / 2]
        }

    return result.toLong()
}
