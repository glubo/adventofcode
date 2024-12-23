package cz.glubo.adventofcode.y2024.day23

import cz.glubo.adventofcode.utils.allSubsets
import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger

val logger = noCoLogger({}.javaClass.toString())

data class Computer(
    val name: String,
    val connections: MutableSet<String> = mutableSetOf(),
)

suspend fun y2024day23part1(input: Input): Long {
    logger.info("year 2024 day 23 part 1")
    val computerMap = mutableMapOf<String, Computer>()
    input.lineFlow().collect { line ->
        val (a, b) = line.split("-")
        val ac = computerMap.computeIfAbsent(a) { Computer(a) }
        val bc = computerMap.computeIfAbsent(b) { Computer(b) }
        ac.connections.add(b)
        bc.connections.add(a)
    }

    val heads = computerMap.keys.filter { it.startsWith("t") }

    val triplets =
        heads
            .flatMap { head ->
                val headComputer = computerMap[head]!!

                val tripletPairs =
                    headComputer.connections
                        .flatMap { a ->
                            headComputer.connections.mapNotNull { b ->
                                if (a < b) a to b else null
                            }
                        }.filter { (a, b) ->
                            computerMap[a]!!.connections.contains(b)
                        }.map { (a, b) ->
                            listOf(head, a, b).sorted()
                        }

                tripletPairs
            }.toSet()

    logger.debug { triplets }
    return triplets.size.toLong()
}

suspend fun y2024day23part2(input: Input): String {
    logger.info("year 2024 day 23 part 2")
    val computerMap = mutableMapOf<String, Computer>()
    input.lineFlow().collect { line ->
        val (a, b) = line.split("-")
        val ac = computerMap.computeIfAbsent(a) { Computer(a) }
        val bc = computerMap.computeIfAbsent(b) { Computer(b) }
        ac.connections.add(b)
        bc.connections.add(a)
    }

    var largestSoFar = setOf<String>()

    computerMap.forEach { k, c ->
        val list = setOf(k) + c.connections
        list
            .allSubsets()
            .forEach { computerSet ->
                if (computerSet.size <= largestSoFar.size) return@forEach

                if (computerSet.all { hasAllConnections(computerMap[it]!!, computerSet) }) {
                    largestSoFar = computerSet
                }
            }
    }

    return largestSoFar
        .sorted()
        .joinToString(",")
}

private fun hasAllConnections(
    computer: Computer,
    candidateList: Set<String>,
): Boolean =
    candidateList.all {
        when {
            it == computer.name -> true
            computer.connections.contains(it) -> true
            else -> false
        }
    }

fun List<String>.isInteresting() =
    this.contains("co") &&
        this.contains("de") &&
        this.contains("ka") &&
        this.contains("ta")
