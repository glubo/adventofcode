package cz.glubo.adventofcode


data class Elf(
    val calories: List<Int>
) {
    fun getTotalCalories() = calories.sum()
}

class ElfChooser {
    fun pushElf(elf: Elf) {
    }
}
