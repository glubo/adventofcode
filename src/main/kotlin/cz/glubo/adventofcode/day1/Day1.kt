package cz.glubo.adventofcode.day1

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.runningFold

data class Elf(
    val calories: List<Int>,
) {
    fun getTotalCalories() = calories.sum()
}

suspend fun Flow<String>.day1p1(): Int {
    val parser = ElfParser()
    val elfChooser = ElfChooser()

    val chosenElf = parser.parseInput(this)
        .runningFold(elfChooser) { accumulator, value ->
            accumulator.pushElf(value)
            accumulator
        }.last()
        .getElfWithMostCalories()

    return chosenElf.getTotalCalories()
}

suspend fun Flow<String>.day1p2(): Int {
    val parser = ElfParser()
    val elfChooser = ElfChooser(mostCaloriesCapacity = 3)

    val totalCaloriesOfChosenElves = parser.parseInput(this)
        .runningFold(elfChooser) { accumulator, value ->
            accumulator.pushElf(value)
            accumulator
        }.last()
        .getElvesWithMostCalories()
        .sumOf { it.getTotalCalories() }

    return totalCaloriesOfChosenElves
}

class ElfChooser(
    val mostCaloriesCapacity: Int = 1,
) {
    private var elvesWithMostCalories: List<Elf> = listOf()

    fun pushElf(elf: Elf) {
        elvesWithMostCalories = (elvesWithMostCalories + elf)
            .sortedByDescending { it.getTotalCalories() }
            .take(mostCaloriesCapacity)
    }

    fun getElfWithMostCalories(): Elf {
        return elvesWithMostCalories.firstOrNull()
            ?: throw ElfNotFound()
    }

    fun getElvesWithMostCalories(): List<Elf> {
        return elvesWithMostCalories
    }

    class ElfNotFound : RuntimeException()
}

class ElfParser {
    fun parseInput(inputLines: Flow<String>): Flow<Elf> = inputLines.windowBy(
        { it.isBlank() },
        { stringList ->
            val calories = stringList.filter { it.isNotBlank() }
                .map { it.toInt() }
            if (calories.isNotEmpty())
                Elf(calories)
            else
                null
        }
    ).filterNotNull()

    fun <T, R> Flow<T>.windowBy(breakWindow: suspend (T) -> Boolean, transform: suspend (List<T>) -> R): Flow<R> {

        return flow {
            val buffer = ArrayDeque<T>()

            collect { value ->
                if (breakWindow(value)) {
                    emit(transform(buffer))
                    buffer.clear()
                } else {
                    buffer.addLast(value)
                }
            }
            emit(transform(buffer))
        }
    }
}