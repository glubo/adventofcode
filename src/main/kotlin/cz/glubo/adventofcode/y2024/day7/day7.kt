package cz.glubo.adventofcode.y2024.day7

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.fold
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("y2024d07")

data class Input(
    val target: Long,
    val numbers: List<Long>,
)

fun parseInput(line: String): Input {
    val (target, remain) = line.split(": ")
    val numbers =
        remain
            .split(' ')
            .map { it.toLong() }
    return Input(target.toLong(), numbers)
}

suspend fun y2024day7part1(inputFlow: Flow<String>): Long =
    inputFlow.fold(0L) { acc: Long, line: String ->
        val input = parseInput(line)
        if (matches1(input.target, input.numbers)) {
            acc + input.target
        } else {
            acc
        }
    }

suspend fun y2024day7part2(inputFlow: Flow<String>): Long =
    inputFlow.fold(0L) { acc: Long, line: String ->
        val input = parseInput(line)
        if (matches2(input.target, input.numbers)) {
            acc + input.target
        } else {
            acc
        }
    }

private fun matches1(
    finalTarget: Long,
    numbers: List<Long>,
) = matches(finalTarget, numbers) { target, current ->
    sequence {
        yield(target - current)
        if (target.rem(current) == 0L) {
            yield(target / current)
        }
    }
}

private fun matches2(
    finalTarget: Long,
    numbers: List<Long>,
) = matches(finalTarget, numbers) { target, current ->
    sequence {
        yield(target - current)
        if (target.rem(current) == 0L) {
            yield(target / current)
        }
        if ("$target".endsWith("$current")) {
            yield("$target".removeSuffix("$current").toLong())
        }
    }
}

fun matches(
    target: Long,
    numbers: List<Long>,
    variants: (Long, Long) -> Sequence<Long>,
): Boolean {
    val current = numbers.last()
    val nextNumbers = numbers.dropLast(1)
    return when {
        numbers.size == 1 -> {
            target == current
        }

        target < 0 -> {
            false
        }

        numbers.size > 1 -> {
            variants(target, current).any {
                matches(it, nextNumbers, variants)
            }
        }

        else -> {
            throw RuntimeException("ugh")
        }
    }
}
