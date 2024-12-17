package cz.glubo.adventofcode.y2024.day17

import cz.glubo.adventofcode.utils.input.Input
import cz.glubo.adventofcode.utils.isSame
import cz.glubo.adventofcode.y2024.day17.ParameterType.COMBO
import cz.glubo.adventofcode.y2024.day17.ParameterType.LITERAL
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.toList
import java.math.BigInteger

val logger = noCoLogger({}.javaClass.toString())

enum class ParameterType {
    LITERAL,
    COMBO,
}

enum class OPCODE(
    val opcode: Int,
    val type: ParameterType,
) {
    ADV(0, COMBO),
    BXL(1, LITERAL),
    BST(2, COMBO),
    JNZ(3, LITERAL),
    BXC(4, LITERAL),
    OUT(5, COMBO),
    BDV(6, COMBO),
    CDV(7, COMBO),
}

data class State(
    var A: BigInteger,
    var B: BigInteger,
    var C: BigInteger,
    var pointer: Int,
    var output: String,
)

typealias Program = List<Pair<OPCODE, Int>>

fun combo(
    state: State,
    param: Int,
): BigInteger =
    when (param) {
        0, 1, 2, 3 -> param.toBigInteger()
        4 -> state.A
        5 -> state.B
        6 -> state.C
        7 -> error("reserved")
        else -> error("unknow combo value $param")
    }

infix fun String.join(other: String) =
    if (this.isNotBlank()) {
        "$this,$other"
    } else {
        other
    }

fun advance(
    state: State,
    program: Program,
): Boolean {
    val op = program.getOrNull((state.pointer / 2).toInt())
    logger.debug { "op $op state $state" }
    return when (op?.first) {
        OPCODE.ADV -> {
            state.A /= pow2(combo(state, op.second))
            state.pointer += 2
            true
        }

        OPCODE.BXL -> {
            state.B = state.B.xor(op.second.toBigInteger())
            state.pointer += 2
            true
        }

        OPCODE.BST -> {
            state.B = combo(state, op.second) % 8.toBigInteger()
            state.pointer += 2
            true
        }

        OPCODE.JNZ -> {
            if (state.A == BigInteger.ZERO) {
                state.pointer += 2
                true
            } else {
                state.pointer = op.second
                true
            }
        }

        OPCODE.BXC -> {
            state.B = state.B.xor(state.C)
            state.pointer += 2
            true
        }

        OPCODE.OUT -> {
            state.output = state.output join combo(state, op.second).mod(8.toBigInteger()).toString()
            state.pointer += 2
            true
        }

        OPCODE.BDV -> {
            state.B = state.A / pow2(combo(state, op.second))
            state.pointer += 2
            true
        }

        OPCODE.CDV -> {
            state.C = state.A / pow2(combo(state, op.second))
            state.pointer += 2
            true
        }

        null -> {
            false
        }
    }
}

fun String.parseProgramRaw() =
    this
        .removePrefix("Program: ")
        .split(",")
        .map { it.toInt() }

fun List<Int>.toProgram() = this
    .chunked(2)
    .map { (o, p) ->
        OPCODE.entries.first { it.opcode == o } to p
    }

fun String.parseProgram() =
    this.parseProgramRaw()
        .toProgram()

suspend fun y2024day17part1(input: Input): String {
    logger.info("year 2024 day 17 part 1")
    val lines = input.lineFlow().toList()
    val a = lines[0].removePrefix("Register A: ").toBigInteger()
    val b = lines[1].removePrefix("Register B: ").toBigInteger()
    val c = lines[2].removePrefix("Register C: ").toBigInteger()
    val program: Program =
        lines[4].parseProgram()

    val currentState =
        State(
            A = a,
            B = b,
            C = c,
            pointer = 0,
            output = "",
        )
    var i = 0
    while (i < 100) {
        val runNext = advance(currentState, program)
        if (!runNext) {
            return currentState.output
        }
        i++
    }
    return currentState.output
}

fun run(
    program: Program,
    a: BigInteger,
): List<Int> {
    val currentState =
        State(
            A = a,
            B = BigInteger.ZERO,
            C = BigInteger.ZERO,
            pointer = 0,
            output = "",
        )
    var i = 0
    while (i < 1000) {
        val runNext = advance(currentState, program)
        if (!runNext) {
            return currentState.output.split(",").map { it.toInt() }
        }
        i++
    }
    return currentState.output.split(",").map { it.toInt() }
}

fun pow2(n: BigInteger): BigInteger =
    if (n < BigInteger.ZERO) {
        throw IllegalArgumentException()
    } else if (n == BigInteger.ZERO) {
        BigInteger.ONE
    } else {
        BigInteger.ONE.shl(n.toInt())
    }

suspend fun y2024day17part2(input: Input): BigInteger {
    val lines = input.lineFlow().toList()
    val wanted = lines[4].parseProgramRaw()
    val program: Program = wanted.toProgram()

    fun findMatch(wanted: List<Int>): List<BigInteger> {
        return when (wanted.size) {
            0 -> listOf(BigInteger.ZERO)
            else -> {
                val smallerACandidates = findMatch(wanted.drop(1))

                smallerACandidates.flatMap { smallerA ->
                    (0..7).mapNotNull { i ->
                        val a = smallerA * 8.toBigInteger() + i.toBigInteger()
                        val output = run(program, a)
                        logger.debug { "$smallerA, $a: $output, $wanted}" }
                        if (wanted.isSame(output)) {
                            logger.debug { "HIT" }
                            a
                        } else {
                            null
                        }
                    }
                }
            }
        }

    }

    val suitableAList = findMatch(wanted)
    logger.debug { suitableAList }
    return suitableAList.minOrNull() ?: error("not found")
}
