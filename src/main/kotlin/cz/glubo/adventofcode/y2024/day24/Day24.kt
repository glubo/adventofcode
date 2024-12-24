package cz.glubo.adventofcode.y2024.day24

import cz.glubo.adventofcode.utils.getBit
import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.toList
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

    val suspects = mutableSetOf<Pair<String, Short>>()
    var rem = badBitmap
    var i = 0
    do {
        val cur = rem.and(1)
        if (cur > 0) {
            val name = "z" + i.toString().padStart(2, '0')
            suspects.add(name to wantedResult.getBit(i).toShort())
        }
        i++
        rem = rem.shr(1)
    } while (rem > 0)

    val suspectMap =
        suspects
            .associateWith { suspect ->
                getNameWhitelist(suspect.first, gates)
            }.entries
            .sortedBy { it.value.size }
    logger.info(suspectMap.map { it.key to it.value.size })

    suspectMap.first().let { sentry ->
        val reducedState =
            stateMap.entries
                .filter { it.key in sentry.value }
                .associate { it.key to it.value }
        logger.info { "reduced state $reducedState" }

        val reducedGates =
            gates.filter {
                it.target in sentry.value
            }
        logger.info { "reduced gates $reducedGates" }

        sentry.value.forEach { a ->
            sentry.value.forEach { b ->
                val swappedResult = computeState(reducedState, reducedGates, mapOf(a to b, b to a))
                logger.info { "swapping $a $b result $swappedResult" }
                if (swappedResult[sentry.key.first] == sentry.key.second) {
                    logger.info("swap $a $b")
                }
            }
        }
    }

//    logger.info { "Initial suspects: $suspects" }
//    do {
//        var newSuspects = mutableSetOf<String>()
//
//        suspects.forEach { suspect ->
//            val suspectGate =
//                gateLines.firstOrNull { it.target == suspect }
//                    ?: return@forEach
//            if (suspectGate.inputA !in suspects) newSuspects.add(suspectGate.inputA)
//            if (suspectGate.inputB !in suspects) newSuspects.add(suspectGate.inputB)
//        }
//
//        suspects.addAll(newSuspects)
//    } while (newSuspects.isEmpty())
//
//    logger.info { "Found suspects: $suspects" }
//
//    val swapPool =
//        suspects
//            .allSubsets()
//            .filter { it.size == swaps * 2 }
//            .firstNotNullOf { swapMembers ->
//                swapMembers
//                    .allOrders()
//                    .firstOrNull {
//                        val swapMap = mutableMapOf<String, String>()
//
//                        it.chunked(2).forEach { (a, b) ->
//                            swapMap[a] = b
//                            swapMap[b] = a
//                        }
//
//                        wantedResult ==
//                            compute(
//                                stateMap,
//                                gateLines,
//                                swapMap,
//                            )
//                    }
//            }
//
//    return swapPool
//        .sorted()
//        .joinToString(",")
    return ""
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

private fun getNameWhitelist(
    target: String,
    gates: List<Gate>,
): Set<String> {
    var suspects = mutableSetOf(target)

    do {
        var newSuspects = mutableSetOf<String>()

        suspects.forEach { suspect ->
            val suspectGate =
                gates.firstOrNull { it.target == suspect }
                    ?: return@forEach
            if (suspectGate.inputA !in suspects) newSuspects.add(suspectGate.inputA)
            if (suspectGate.inputB !in suspects) newSuspects.add(suspectGate.inputB)
        }

        suspects.addAll(newSuspects)
    } while (newSuspects.isNotEmpty())
    return suspects
}
