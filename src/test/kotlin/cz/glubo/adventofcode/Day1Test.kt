@file:OptIn(ExperimentalCoroutinesApi::class)

package cz.glubo.adventofcode

import cz.glubo.adventofcode.day1.Elf
import cz.glubo.adventofcode.day1.ElfChooser
import cz.glubo.adventofcode.day1.ElfParser
import cz.glubo.adventofcode.day1.day1p1
import cz.glubo.adventofcode.day1.day1p2
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

/**
 * https://adventofcode.com/2022/day/1
 */
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
        assertAll(
            { assertEquals(1, elfCollection.count()) },
            { assertEquals(123, elfCollection.first().getTotalCalories()) },
        )
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
        assertAll(
            { assertEquals(1, elfCollection.count()) },
            { assertEquals(333, elfCollection.first().getTotalCalories()) },
        )
    }

    @Test
    fun `Multiple elves parsing`() = runTest {
        val parser = ElfParser()

        val outputFlow = parser.parseInput(
            listOf(
                "123",
                "",
                "210"
            ).asFlow()
        )

        val elfCollection = outputFlow.toCollection(mutableListOf())
        assertAll(
            { assertEquals(2, elfCollection.count()) },
            { assertEquals(123, elfCollection[0].getTotalCalories()) },
            { assertEquals(210, elfCollection[1].getTotalCalories()) },
        )
    }

    @Test
    fun `Example from Aoc day1 part1`() = runTest {
        val calories = listOf(
            "1000",
            "2000",
            "3000",
            "",
            "4000",
            "",
            "5000",
            "6000",
            "",
            "7000",
            "8000",
            "9000",
            "",
            "10000",
        ).asFlow().day1p1()

        assertEquals(24000, calories)
    }

    @Test
    fun `ElfChooser can pick two most caloric Elves`() {
        val elfChooser = ElfChooser(mostCaloriesCapacity = 2)
        val elf1 = Elf(listOf(111))
        val elf2 = Elf(listOf(222))
        val elf3 = Elf(listOf(333))

        elfChooser.pushElf(elf1)
        elfChooser.pushElf(elf2)
        elfChooser.pushElf(elf3)

        val chosenElves = elfChooser.getElvesWithMostCalories()
        assertAll(
            { assertEquals(2, chosenElves.size) },
            { assertEquals(chosenElves[0], elf3) },
            { assertEquals(chosenElves[1], elf2) },
        )
    }

    @Test
    fun `Example from Aoc day1 part2`() = runTest {
        val calories = listOf(
            "1000",
            "2000",
            "3000",
            "",
            "4000",
            "",
            "5000",
            "6000",
            "",
            "7000",
            "8000",
            "9000",
            "",
            "10000",
        ).asFlow().day1p2()

        assertEquals(45000, calories)
    }
}