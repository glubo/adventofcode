package cz.glubo.adventofcode

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day1Test {
    @Test
    fun `ElfChooser can accept Elf`() {
        val elfChooser = ElfChooser()
        elfChooser.pushElf(
            Elf(emptyList())
        )
    }

    @Test
    fun `ElfChooser will throw ElfNotFound when no elf was pushed`() {
        val elfChooser = ElfChooser()
        Assertions.assertThrows(
            ElfChooser.ElfNotFound::class.java
        ) {
            elfChooser.getElfWithMostCalories()
        }
    }

    @Test
    fun `ElfChooser will return single Elf`() {
        val elfChooser = ElfChooser()
        val elf = Elf(emptyList())
        elfChooser.pushElf(elf)
        Assertions.assertEquals(elfChooser.getElfWithMostCalories(), elf)
    }

}