package cz.glubo.adventofcode

import java.lang.RuntimeException

data class Elf(
    val calories: List<Int>
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

    class ElfNotFound: RuntimeException()
}
