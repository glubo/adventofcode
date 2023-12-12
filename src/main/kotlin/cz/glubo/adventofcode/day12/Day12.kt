package cz.glubo.adventofcode.day12

import io.klogging.noCoLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.fold

private val logger = noCoLogger({}.javaClass.`package`.toString())

suspend fun Flow<String>.day12part1(): Long {
    return this.fold(0L) { acc, line ->
        val (source, ruleString) = line.split(' ')
        val rules =
            ruleString.split(',')
                .map { it.toInt() }

        var variants = variations(
            Input(
                source,
                rules
            )
        )
        logger.debug { "source: '$source' rules: '$rules' variants: $variants" }

        acc + variants
    }
}

suspend fun Flow<String>.day12part2(): Long {
    return this.fold(0L) { acc, line ->
        val (sourceIn, ruleString) = line.split(' ')
        val ruleStringR = "$ruleString,$ruleString,$ruleString,$ruleString,$ruleString"
        val rules =
            ruleStringR.split(',')
                .map { it.toInt() }
        val source = "$sourceIn?$sourceIn?$sourceIn?$sourceIn?$sourceIn"
        val validCount = variations(
            Input(
                source,
                rules
            )
        )
        acc + validCount
    }
}

data class Input(
    val tail: String,
    val rulesLeft: List<Int>,
)

fun ifb(condition: () -> Boolean) = if (condition()) 1L else 0L
fun String.removePrefix(char: Char) = this.dropWhile { it == char }
fun String.prefixLength(chars: CharSequence) = this.takeWhile { it in chars }.length
fun String.replaceFirst(char: Char) = this.replaceRange(0, 1, char.toString())

fun tapDebug(input: Input, function: (Input) -> Long): Long {
    val result = function(input)

    logger.debug { "for input $input got $result" }

    return result
}

fun variations(input: Input) = buildMap<Input, Long> {
    fun rec(i: Input): Long = getOrPut(i) {
        tapDebug(i) {
            when {
                i.tail.isEmpty() -> ifb { i.rulesLeft.isEmpty() }
                i.rulesLeft.isEmpty() -> ifb { !i.tail.contains('#') }
                else -> when (i.tail.first()) {
                    '.' -> rec(
                        Input(
                            i.tail.removePrefix('.'),
                            i.rulesLeft
                        )
                    )

                    '#' -> {
                        if (i.rulesLeft.first() > i.tail.prefixLength("#?")) {
                            0L
                        } else {
                            val newTail = i.tail.drop(i.rulesLeft.first())
                            when {
                                newTail.isEmpty() -> ifb { i.rulesLeft.size == 1 }
                                newTail.startsWith('#') -> 0L
                                newTail.startsWith('?') ->
                                    rec(
                                        Input(
                                            newTail.drop(1),
                                            i.rulesLeft.drop(1)
                                        )
                                    )
                                newTail.startsWith('.') ->
                                    rec(
                                        Input(
                                            newTail.removePrefix('.'),
                                            i.rulesLeft.drop(1)
                                        )
                                    )

                                else -> throw RuntimeException("asdasd")
                            }
                        }
                    }

                    '?' -> rec(
                        Input(
                            i.tail.replaceFirst('#'),
                            i.rulesLeft
                        )
                    ) + rec(
                        Input(
                            i.tail.replaceFirst('.'),
                            i.rulesLeft
                        )
                    )

                    else -> 0L
                }
            }
        }
    }
    rec(input)
}[input]!!

