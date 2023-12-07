package cz.glubo.adventofcode.day6

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

fun simulate(time: Long, dist: Long) = (0..time).filter { chargeTime ->
    val goTime = time - chargeTime
    val speed = chargeTime
    val goDist = goTime * speed
    goDist > dist
}.size

suspend fun Flow<String>.day6part1(): Int {
    val lines = this.toList()
    val times = lines.first()
        .removePrefix("Time:")
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.toLong() }

    val distances = lines[1]
        .removePrefix("Distance:")
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.toLong() }


    return times.indices.fold(1) { acc, i ->
        acc * simulate(times[i], distances[i])
    }

}

suspend fun Flow<String>.day6part2(): Int {
    val lines = this.toList()
    val time = lines.first()
        .removePrefix("Time:")
        .split(" ")
        .filter { it.isNotBlank() }
        .joinToString("")
        .toLong()

    val distance = lines[1]
        .removePrefix("Distance:")
        .split(" ")
        .filter { it.isNotBlank() }
        .joinToString("")
        .toLong()


    return simulate(time, distance)
}
