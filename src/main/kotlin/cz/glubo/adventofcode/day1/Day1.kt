package cz.glubo.adventofcode.day1

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningFold
import java.lang.RuntimeException

data class Elf(
    val calories: List<Int>,
) {
    fun getTotalCalories() = calories.sum()
}

class ElfChooser {
    private var elfWithMostCalories: Elf? = null

    fun pushElf(elf: Elf) {
        val currentTarget = elfWithMostCalories?.getTotalCalories() ?: Int.MIN_VALUE
        if (elf.getTotalCalories() > currentTarget) {
            elfWithMostCalories = elf
        }
    }

    fun getElfWithMostCalories(): Elf {
        return elfWithMostCalories
            ?: throw ElfNotFound()
    }

    class ElfNotFound : RuntimeException()
}

class ElfParser {
    suspend fun parseInput(inputLines: Flow<String>): Flow<Elf> = listOf(inputLines.runningFold(emptyList<Int>()) { accumulator, value ->
        accumulator + value.toInt()
    }.last()
        .let {
            if (it.isNotEmpty()) {
                Elf(
                    it
                )
            } else {
                null
            }
        })
        .filterNotNull()
        .asFlow()
}