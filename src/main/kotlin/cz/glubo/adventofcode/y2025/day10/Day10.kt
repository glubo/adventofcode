package cz.glubo.adventofcode.y2025.day10

import com.microsoft.z3.Context
import com.microsoft.z3.Status
import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.fold
import kotlin.math.abs

val logger = noCoLogger({}.javaClass.toString())

val lineRegex = """\[(?<lights>[^\[]*)] (?<buttons>[^{]*) \{(?<joltage>.*)}""".toRegex()

suspend fun y2025day10part1(input: Input): Long {
    logger.info("year 2025 day 10 part 1")
    return input.lineFlow().fold(0L) { acc, line ->
        val (lightsPart, buttonsPart, joltagePart) =
            lineRegex
                .matchEntire(line)
                ?.destructured
                ?: error("line '$line' does not parse")
        logger.debug { "$lightsPart $buttonsPart" }

        val wantedLights =
            lightsPart.map {
                when (it) {
                    '#' -> true
                    '.' -> false
                    else -> error("unexpected light char $it")
                }
            }

        val buttons =
            buttonsPart
                .split(' ')
                .map { button ->
                    button
                        .removePrefix("(")
                        .removeSuffix(")")
                        .split(",")
                        .map { it.toInt() }
                }

        logger.debug { "$wantedLights $buttons" }

        var i = 0
        var states = setOf(wantedLights.map { false })
        do {
            i++
            val nextStates =
                states.flatMap { state ->
                    buttons.map { button ->
                        state
                            .mapIndexed { index, bool ->
                                if (button.contains(index)) {
                                    !bool
                                } else {
                                    bool
                                }
                            }.also { if (it == wantedLights) return@fold acc + i.toLong() }
                    }
                }
            states = nextStates.toSet()
        } while (i < 10000)

        acc
    }
}

data class State(
    val low: Long = 0L,
    val high: Long = 0L,
) : Comparable<State> {
    override fun toString(): String = "State(low: " + (0..7).map { low.getAt(it) } + ", high: " + (0..7).map { high.getAt(it) } + ")"

    fun increment(index: Int) =
        when {
            index < 7 -> State(low + xyz(index), high)
            index < 14 -> State(low + xyz(index), high)
            else -> error("Index too large $index")
        }

    operator fun plus(other: State) = State(low + other.low, high + other.high)

    fun isOver(wanted: State): Boolean =
        (0..7).any {
            low.getAt(it) > wanted.low.getAt(it) ||
                high.getAt(it) > wanted.high.getAt(it)
        }

    private fun Long.getAt(index: Int) = (this and zyx(index)).shr(8 * (index % 7))

    private fun zyx(index: Int) = 255L.shl(8 * (index % 7))

    private fun xyz(index: Int) = 1L.shl(8 * (index % 7))

    override fun compareTo(other: State) =
        (0..7)
            .sumOf { index ->
                abs(low.getAt(index) - other.low.getAt(index)) +
                    abs(high.getAt(index) - other.high.getAt(index))
            }.toInt()
}

data class Head(
    val state: State,
    val length: Int,
) : Comparable<Head> {
    override fun compareTo(other: Head) =
        when (val c = state.compareTo(other.state)) {
            0 -> length.compareTo(other.length)
            else -> c
        }
}

fun solveWithZ3(
    buttons: List<List<Int>>,
    target: List<Int>,
): Long {
    Context().use { ctx ->
        val optimizer = ctx.mkOptimize()

        val buttonVars =
            buttons.indices.map { i ->
                val btnVar = ctx.mkIntConst("btn_$i")
                optimizer.Add(ctx.mkGe(btnVar, ctx.mkInt(0)))
                btnVar
            }

        target.indices.forEach { counterIndex ->
            val affectingButtonsTerms =
                buttons
                    .mapIndexedNotNull { btnIdx, affectedIndices ->
                        if (affectedIndices.contains(counterIndex)) {
                            buttonVars[btnIdx]
                        } else {
                            null
                        }
                    }.toTypedArray()

            val sumExpression = ctx.mkAdd(*affectingButtonsTerms)

            optimizer.Add(ctx.mkEq(sumExpression, ctx.mkInt(target[counterIndex])))
        }

        val totalPresses = ctx.mkAdd(*buttonVars.toTypedArray())
        val minHandle = optimizer.MkMinimize(totalPresses)

        val status = optimizer.Check()

        return if (status == Status.SATISFIABLE) {
            minHandle.lower.toString().toLong()
        } else {
            -1L
        }
    }
}

suspend fun y2025day10part2(input: Input): Long {
    logger.info("year 2025 day 10 part 2")
    return input.lineFlow().fold(0L) { acc, line ->
        val (lightsPart, buttonsPart, joltagePart) =
            lineRegex
                .matchEntire(line)
                ?.destructured
                ?: error("line '$line' does not parse")
        logger.info { "$buttonsPart $joltagePart" }

        val wantedJoltages =
            joltagePart
                .removePrefix("{")
                .removeSuffix("}")
                .split(",")
                .map {
                    it.toInt()
                }

        val buttons =
            buttonsPart
                .split(' ')
                .map { button ->
                    button
                        .removePrefix("(")
                        .removeSuffix(")")
                        .split(",")
                        .map { it.toInt() }
                }

        acc + solveWithZ3(buttons, wantedJoltages)
    }
}
