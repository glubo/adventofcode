package cz.glubo.adventofcode.y2024.day25

import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.toList

val logger = noCoLogger({}.javaClass.toString())

fun parseItemAndPush(
    buf: MutableList<String>,
    items: MutableList<List<Int>>,
): Int {
    val item =
        buf.first().indices.map { i ->
            buf.sumOf {
                val height = if (it[i] == '#') 1 else 0
                height
            } - 1
        }

    items.addLast(item)

    return buf.size - 1
}

fun parseLockOrKeyAndPushIt(
    buf: MutableList<String>,
    keys: MutableList<List<Int>>,
    locks: MutableList<List<Int>>,
): Int =
    when {
        buf.isEmpty() -> 0
        buf.first().all { it == '#' } -> parseItemAndPush(buf, keys)
        buf.last().all { it == '#' } -> parseItemAndPush(buf, locks)
        else -> error("Not a key or a lock: $buf")
    }

suspend fun y2024day25part1(input: Input): Long {
    logger.info("year 2024 day 25 part 1")
    val keys = mutableListOf<List<Int>>()
    val locks = mutableListOf<List<Int>>()
    var buf = mutableListOf<String>()
    var height: Int = 0
    input
        .lineFlow()
        .toList()
        .forEach { line ->
            if (line.isNotBlank()) {
                buf += line
            } else {
                height = parseLockOrKeyAndPushIt(buf, keys, locks)
                buf.clear()
            }
        }
    parseLockOrKeyAndPushIt(buf, keys, locks)

    return keys
        .sumOf { key ->
            locks.count { lock ->
                key.indices.all { i ->
                    (key[i] + lock[i]) < height
                }
            }
        }.toLong()
}

suspend fun y2024day25part2(input: Input): Long {
    logger.info("year 2024 day 25 part 2")
    return 0
}
