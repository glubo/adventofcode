package cz.glubo.adventofcode

import java.lang.RuntimeException

data class Elf(
    val calories: List<Int>
) {
    fun getTotalCalories() = calories.sum()
}

class ElfChooser {
    fun pushElf(elf: Elf) {
    }

    fun getElfWithMostCalories(): Elf {
        throw ElfNotFound()
    }

    class ElfNotFound: RuntimeException()
}
