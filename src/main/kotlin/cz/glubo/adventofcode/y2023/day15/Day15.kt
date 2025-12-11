package cz.glubo.adventofcode.y2023.day15

import io.klogging.noCoLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

private val logger = noCoLogger({}.javaClass.`package`.toString())

fun String.myHash() =
    this
        .fold(0) { acc, char ->
            val currentAscii = char.code

            ((acc + currentAscii) * 17) % 256
        }.toLong()

suspend fun Flow<String>.y2023day15part1(): Long =
    this
        .first()
        .split(",")
        .sumOf { it.myHash() }

data class Lens(
    val label: String,
    val focalLength: Int,
)

suspend fun Flow<String>.y2023day15part2(): Long {
    val map: Map<Int, ArrayDeque<Lens>> =
        (0..255).associate {
            it to ArrayDeque<Lens>()
        }

    val setRegex = """(?<label>\w+)=(?<param>\d+)""".toRegex()
    val delRegex = "(?<label>\\w+)-".toRegex()
    this
        .first()
        .split(",")
        .forEach { op ->
            when {
                op.matches(setRegex) -> {
                    val match = setRegex.matchEntire(op)!!
                    val label = match.groups["label"]!!.value
                    val param = match.groups["param"]!!.value.toInt()
                    val boxId = label.myHash().toInt()
                    val box: ArrayDeque<Lens> = map[boxId]!!
                    logger.debug { "Set $label@$boxId with $param" }
                    if (box.any { it.label == label }) {
                        val at = box.indexOfFirst { it.label == label }
                        box[at] = Lens(label, param)
                    } else {
                        box.addLast(Lens(label, param))
                    }
                }

                op.matches(delRegex) -> {
                    val match = delRegex.matchEntire(op)!!
                    val label = match.groups["label"]!!.value
                    val boxId = label.myHash().toInt()
                    val box: ArrayDeque<Lens> = map[boxId]!!
                    logger.debug { "Delete $label@$boxId" }
                    val element = box.firstOrNull { it.label == label }
                    element?.let { box.remove(it) }
                }

                else -> {
                    throw RuntimeException("Unknown operation: '$op'")
                }
            }
            logger.debug { "After $op: " + map.filter { it.value.isNotEmpty() }.toMap() }
        }
    return map.entries.sumOf { boxEntry ->
        var sum = 0L
        boxEntry.value.forEachIndexed { slot, lens ->
            val value = (boxEntry.key + 1) * (slot + 1) * lens.focalLength
            logger.debug { value }
            sum += value
        }
        sum
    }
}
