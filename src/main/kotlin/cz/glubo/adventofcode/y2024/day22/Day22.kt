package cz.glubo.adventofcode.y2024.day22

import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.fold

val logger = noCoLogger({}.javaClass.toString())

fun step(prev: Long): Long {
    var secret = prev
    secret = secret.xor(secret.shl(6))
    secret = secret.and(0xFFFFFF)
    secret = secret.xor(secret.shr(5))
    secret = secret.and(0xFFFFFF)
    secret = secret.xor(secret.shl(11))
    secret = secret.and(0xFFFFFF)

    return secret
}

suspend fun y2024day22part1(input: Input): Long {
    logger.info("year 2024 day 22 part 1")
    return input.lineFlow().fold(0L) { acc: Long, line: String ->

        var secret = line.toLong()
        repeat(2000) { secret = step(secret) }
        acc + secret
    }
}

data class Step(
    val price: Int,
    val diff: Int?,
)

suspend fun y2024day22part2(input: Input): Long {
    logger.info("year 2024 day 22 part 2")

    val globalMap = mutableMapOf<List<Int>, Int>()

    input.lineFlow().collect { line: String ->
        var secret = line.toLong()
        val firstPrice = (secret % 10).toInt()
        var lastPrice: Int = firstPrice
        val localMap = mutableMapOf<List<Int>, Int>()
        var stack =
            mutableListOf(
                Step(firstPrice, null),
            )
        repeat(2000) {
            secret = step(secret)

            val curPrice = (secret % 10).toInt()
            stack.addLast(
                Step(
                    curPrice,
                    curPrice - lastPrice,
                ),
            )
            if (stack.size > 4) stack.removeFirst()

            val key = stack.mapNotNull { it.diff }

            if (key.size == 4) {
                localMap.computeIfAbsent(key) { curPrice }
            }
            if (key.size > 4) error("wtf")
            lastPrice = curPrice
        }

        localMap.forEach { local ->
            globalMap.compute(local.key) { _, global ->
                (global ?: 0) + local.value
            }
        }
    }
    return globalMap
        .maxOf { it.value }
        .toLong()
}
