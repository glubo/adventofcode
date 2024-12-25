package cz.glubo.adventofcode.y2024.day24

import cz.glubo.adventofcode.utils.bitDistance
import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.experimental.xor

val logger = noCoLogger({}.javaClass.toString())

suspend fun y2024day24part1(input: Input): Long {
    logger.info("year 2024 day 24 part 1")

    val lines = input.lineFlow().toList()
    val stateLines = lines.takeWhile { it.isNotBlank() }
    var gates = parseGates(lines.takeLastWhile { it.isNotBlank() })

    val stateMap = parseStateMap(stateLines)

    val result =
        compute(
            stateMap,
            gates,
            emptyMap(),
        )
    return result
}

enum class Operation(
    val compute: (Short, Short) -> Short,
) {
    AND({ a, b -> a.and(b) }),
    OR({ a, b -> a.or(b) }),
    XOR({ a, b -> a.xor(b) }),
}

data class Gate(
    val inputA: String,
    val inputB: String,
    val operation: Operation,
    val target: String,
)

fun computeState(
    inputStateMap: Map<String, Short>,
    gates: List<Gate>,
    swapMap: Map<String, String>,
): Map<String, Short> {
    val stateMap = inputStateMap.toMutableMap()
    var operationsLeft = gates
    do {
        var removed = false

        operationsLeft =
            operationsLeft.filterIndexed { index, op ->
                if (!stateMap.contains(op.inputA) || !stateMap.contains(op.inputB)) {
                    return@filterIndexed true
                }
                removed = true
                val result =
                    op.operation.compute(
                        stateMap[op.inputA]!!,
                        stateMap[op.inputB]!!,
                    )

                val target = swapMap.getOrDefault(op.target, op.target)
                stateMap[target] = result

                false
            }
    } while (removed)
    return stateMap
}

fun compute(
    inputStateMap: Map<String, Short>,
    gates: List<Gate>,
    swapMap: Map<String, String>,
): Long {
    val stateMap = computeState(inputStateMap, gates, swapMap)

    return stateMap.variable("z")
}

fun Map<String, Short>.variable(prefix: String) =
    this
        .filter { (key, value) ->
            key.startsWith(prefix)
        }.entries
        .sortedByDescending { it.key }
        .fold(0L) { acc, it -> acc.shl(1) + it.value }

suspend fun y2024day24part2(
    input: Input,
    swaps: Int,
): String {
    logger.info("year 2024 day 24 part 2")
    val lines = input.lineFlow().toList()
    val stateLines = lines.takeWhile { it.isNotBlank() }
    var gates = parseGates(lines.takeLastWhile { it.isNotBlank() })

    val stateMap = parseStateMap(stateLines)

    val x = stateMap.variable("x")
    val y = stateMap.variable("y")
    val wantedResult = x + y

    val badResult = compute(stateMap, gates, emptyMap())
    val badBitmap = badResult.xor(wantedResult)

    val wantedS = wantedResult.toString(2)
    logger.info { "wantedResult: $wantedS" }
    logger.info { "badbadResult: ${badResult.toString(2)}" }
    logger.info { "badResultBmap ${badBitmap.toString(2).padStart(wantedS.length, '0')}" }
    val a =
        findSwaps(
            wantedResult,
            stateMap,
            gates,
            emptyMap(),
            4,
        )

    return a!!.joinToString(",")
}

suspend fun findSwaps(
    wantedResult: Long,
    stateMap: MutableMap<String, Short>,
    gates: List<Gate>,
    swapMap: Map<String, String>,
    i: Int,
): List<String>? {
    val names =
        gates
            .map { it.target }
            .filter { !swapMap.containsKey(it) }

    val swapErrorList =
        (0..<names.size)
            .flatMap { i ->
                (i + 1..<names.size).map { j ->
                    val newSwapMap =
                        mapOf(
                            names[i] to names[j],
                            names[j] to names[i],
                        ) + swapMap
                    val result = compute(stateMap, gates, newSwapMap)

                    newSwapMap to result.bitDistance(wantedResult)
                }
            }.sortedBy { it.second }
            .toMutableList()

    do {
        runBlocking(Dispatchers.IO) {
            val chunk = (1..3).map { _ -> swapErrorList.removeFirst() }
            val results =
                chunk.map {
                    async {
                        if (it.second == 0) {
                            logger.info { "found ${it.first}" }
                            return@async it.first.keys.sorted()
                        }
                        if (i > 1) {
                            findSwaps(
                                wantedResult,
                                stateMap,
                                gates,
                                it.first,
                                i - 1,
                            )?.let { s -> return@async s }
                        }
                        null
                    }
                }
            results.awaitAll().filterNotNull()
        }.let {
            if (it.isNotEmpty()) return it.first()
        }
    } while (swapErrorList.isNotEmpty())

//    swapErrorList.forEach {
//        if (it.second == 0) {
//            logger.info { "found ${it.first}" }
//            return it.first.keys.sorted()
//        }
//        if (i > 1) {
//            findSwaps(
//                wantedResult,
//                stateMap,
//                gates,
//                it.first,
//                i - 1,
//            )?.let { s -> return s }
//        }
//        null
//    }
    return null
}

private fun parseStateMap(stateLines: List<String>): MutableMap<String, Short> {
    val stateMap = mutableMapOf<String, Short>()

    stateLines.forEach { line ->
        val (name, value) = line.split(": ")
        stateMap[name] = value.toShort()
    }
    return stateMap
}

private fun parseGates(lines: List<String>) =
    lines
        .map { line ->
            val (a, operand, b, _, target) = line.split(" ")
            Gate(
                a,
                b,
                when (operand) {
                    "AND" -> Operation.AND
                    "OR" -> Operation.OR
                    "XOR" -> Operation.XOR
                    else -> error("unknown operand $operand")
                },
                target,
            )
        }
