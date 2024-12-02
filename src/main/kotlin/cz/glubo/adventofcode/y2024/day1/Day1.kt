package cz.glubo.adventofcode.y2024.day1

import kotlinx.coroutines.flow.Flow
import java.lang.Math.abs

suspend fun y2024day1part1(input: Flow<String>): Long {
    val first = mutableListOf<Int>()
    val second = mutableListOf<Int>()

    input.collect {
        val (a, b) = it.split("   ")
        first.add(a.toInt())
        second.add(b.toInt())
    }

    first.sort()
    second.sort()

    var res = first.indices.fold(0L) { acc, i ->
        acc + abs(first[i] - second[i])
    }
    return res
}

suspend fun y2024day1part2(input: Flow<String>): Long {
    val first = mutableListOf<Int>()
    val second = mutableMapOf<Int, Int>()

    input.collect {
        val (a, b) = it.split("   ")
        first.add(a.toInt())
        second.compute(b.toInt(), { c, d ->
            c + (d ?: 0)
        })
    }


    var res = first.fold(0L) { acc, number ->
        acc + second.getOrDefault(number, 0)
    }
    return res
}
