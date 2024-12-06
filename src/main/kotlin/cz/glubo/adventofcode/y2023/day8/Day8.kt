package cz.glubo.adventofcode.y2023.day8

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

fun <T> List<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }

suspend fun Flow<String>.y2023day8part1(): Int {
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

    return navigate(directions, graph, "AAA") { it == "ZZZ" }
}

private fun navigate(
    directions: List<Char>,
    graph: MutableMap<String, Pair<String, String>>,
    initialPosition: String,
    endCondition: (String) -> Boolean,
): Int {
    var ret = 0
    directions.repeat().fold(initialPosition) { position, direction ->
        if (endCondition(position)) {
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

private fun gcd(
    m: Long,
    n: Long,
): Long {
    if (n == 0L) {
        return m
    }
    return gcd(n, m % n)
}

private fun lcm(
    m: Long,
    n: Long,
): Long = m * n / gcd(m, n)

suspend fun Flow<String>.y2023day8part2(): Long {
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

    val startingPositions = graph.keys.filter { it.endsWith("A") }

    return startingPositions.fold(1) { lcm, start ->
        val current = navigate(directions, graph, start) { it.endsWith('Z') }
        lcm(lcm, current.toLong())
    }
}
