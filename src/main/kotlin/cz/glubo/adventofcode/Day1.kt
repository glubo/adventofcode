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
        elfWithMostCalories = elf
    }

    fun getElfWithMostCalories(): Elf {
        return elfWithMostCalories
            ?: throw ElfNotFound()
    }

    class ElfNotFound: RuntimeException()
}
