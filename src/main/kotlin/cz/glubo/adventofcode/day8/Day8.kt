package cz.glubo.adventofcode.day8

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

fun <T> List<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }

suspend fun Flow<String>.day8part1(): Int {
    val lines = this.toList()
    val directions = lines.first().toList()

    val graph = mutableMapOf<String, Pair<String, String>>()
    lines.drop(2).forEach { line ->
        val keyPair = line.split(" = ")
        val key = keyPair.first()
        val valuePair = keyPair[1].split(", ")
        val value = valuePair[0].removePrefix("(") to valuePair[1].removeSuffix(")")
        graph[key] = value
    }

    var ret = 0
    directions.repeat().fold("AAA") { position, direction ->
        if (position == "ZZZ") {
            return ret
        }

        ret++

        when (direction) {
            'L' -> graph[position]!!.first
            'R' -> graph[position]!!.second
            else -> throw RuntimeException("nope")
        }
    }

    return 0
}

suspend fun Flow<String>.day8part2(): Int {
    val lines = this.toList()
    val directions = lines.first().toList()

    val graph = mutableMapOf<String, Pair<String, String>>()
    lines.drop(2).forEach { line ->
        val keyPair = line.split(" = ")
        val key = keyPair.first()
        val valuePair = keyPair[1].split(", ")
        val value = valuePair[0].removePrefix("(") to valuePair[1].removeSuffix(")")
        graph[key] = value
    }

    var ret = 0L

    val startingPositions = graph.keys.filter { it.endsWith("A") }

    directions.repeat().fold(startingPositions) { positions, direction ->
        if (ret % 10000000 == 0L) {
            println("$ret: $positions")
        }
        if (positions.all { it.endsWith("Z") }) {
            println("$ret: $positions")
            return ret.toInt()
        }

        ret++

        when (direction) {
            'L' -> positions.map { graph[it]!!.first }
            'R' -> positions.map { graph[it]!!.second }
            else -> throw RuntimeException("nope")
        }
    }

    return 0
}
