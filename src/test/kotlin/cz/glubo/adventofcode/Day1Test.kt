package cz.glubo.adventofcode

import cz.glubo.adventofcode.day1.Elf
import cz.glubo.adventofcode.day1.ElfChooser
import cz.glubo.adventofcode.day1.ElfParser
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
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
        assertEquals(elfChooser.getElfWithMostCalories(), elf)
    }

    @Test
    fun `ElfChooser will return elf with most calories`() {
        val elfChooser = ElfChooser()
        val elfLowCalories = Elf(listOf(1))
        val elfHighCalories = Elf(listOf(333))

        elfChooser.pushElf(elfLowCalories)
        elfChooser.pushElf(elfHighCalories)

        assertEquals(elfChooser.getElfWithMostCalories(), elfHighCalories)
    }

    @Test
    fun `ElfChooser will return elf with most calories inverted input`() {
        val elfChooser = ElfChooser()
        val elfLowCalories = Elf(listOf(1))
        val elfHighCalories = Elf(listOf(333))

        elfChooser.pushElf(elfHighCalories)
        elfChooser.pushElf(elfLowCalories)

        assertEquals(elfChooser.getElfWithMostCalories(), elfHighCalories)
    }

    @Test
    fun `Empty input yields no elfs`() = runTest {
        val parser = ElfParser()

        val outputFlow = parser.parseInput(emptyList<String>().asFlow())

        assertEquals(0, outputFlow.count())
    }

    @Test
    fun `Simple elf parsing`() = runTest {
        val parser = ElfParser()

        val outputFlow = parser.parseInput(
            listOf(
                "123"
            ).asFlow()
        )

        val elfCollection = outputFlow.toCollection(mutableListOf())
        assertEquals(1, elfCollection.count())
        assertEquals(123, elfCollection.first().getTotalCalories())
    }

    @Test
    fun `Multiline elf parsing`() = runTest {
        val parser = ElfParser()

        val outputFlow = parser.parseInput(
            listOf(
                "123",
                "210"
            ).asFlow()
        )

        val elfCollection = outputFlow.toCollection(mutableListOf())
        assertEquals(1, elfCollection.count())
        assertEquals(333, elfCollection.first().getTotalCalories())
    }

}