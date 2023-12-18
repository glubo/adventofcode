package cz.glubo.adventofcode.day13

import io.klogging.noCoLogger
import kotlinx.coroutines.flow.Flow
import kotlin.math.min

private val logger = noCoLogger({}.javaClass.`package`.toString())

// helper to solve smart-cast hell
private const val IZERO: Int = 0

suspend fun Flow<String>.day13part1(): Long {
    return this.day13(0)
}

suspend fun Flow<String>.day13part2(): Long {
    return this.day13(1)
}

private suspend fun Flow<String>.day13(smudges: Int): Long {
    val buffer = ArrayDeque<String>()
    var sum = 0L

    this.collect {
        if (it == "") {
            sum += weightReflection(buffer, smudges)
            buffer.clear()
        } else {
            buffer.addLast(it)
        }
    }
    if (buffer.isNotEmpty()) sum += weightReflection(buffer, smudges)

    return sum
}

fun weightReflection(
    field: List<String>,
    wantedSmudges: Int,
): Long {
    logger.debug { "\n" + field.mapIndexed { index, s -> "$index: $s" }.joinToString("\n") }
    val horizontalRange = field.first().indices
    val verticalRange = field.indices

    (1..<field.size).forEach { mirrorLine ->
        val size = min(mirrorLine, field.size - mirrorLine)
        val lower = (mirrorLine..mirrorLine + size - 1)
        val upper = (mirrorLine - 1 downTo mirrorLine - size)
        val smudges =
            lower.zip(upper).sumOf { lines ->
                val smudges =
                    horizontalRange.sumOf {
                        if (field[lines.first][it] != field[lines.second][it]) 1 else IZERO
                    }
                logger.debug("${lines.first} ${lines.second} $smudges")
                smudges
            }
        if (smudges == wantedSmudges) {
            logger.debug("Found horizontal mirror line at $mirrorLine with $smudges smudges")
            return 100L * mirrorLine
        }
    }

    (1..<field.first().length).forEach { mirrorColumn ->
        val size = min(mirrorColumn, field.first().length - mirrorColumn)
        val lower = (mirrorColumn..mirrorColumn + size - 1)
        val upper = (mirrorColumn - 1 downTo mirrorColumn - size)
        val smudges =
            lower.zip(upper).sumOf { columns ->
                val smudges =
                    verticalRange.sumOf {
                        if (field[it][columns.first] != field[it][columns.second]) 1 else IZERO
                    }
                logger.debug("${columns.first} ${columns.second} $smudges")
                smudges
            }
        if (smudges == wantedSmudges) {
            logger.debug("Found vertical mirror line at $mirrorColumn with $smudges smudges")
            return 1L * mirrorColumn
        }
    }

    return 0
}
