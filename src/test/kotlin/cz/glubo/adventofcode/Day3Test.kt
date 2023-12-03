@file:OptIn(ExperimentalCoroutinesApi::class)

package cz.glubo.adventofcode.day2

import cz.glubo.adventofcode.day3.day3part1
import cz.glubo.adventofcode.day3.day3part2
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/2
 */
class Day3Test : FreeSpec({
    "We can solve the example" {
        flowOf(
            "467..114..",
            "...*......",
            "..35..633.",
            "......#...",
            "617*......",
            ".....+.58.",
            "..592.....",
            "......755.",
            "...$.*....",
            ".664.598..",
        ).day3part1() shouldBe 4361
    }

    "We can solve the example without last char" {
        flowOf(
            "467..114.",
            "...*.....",
            "..35..633",
            "......#..",
            "617*.....",
            ".....+.58",
            "..592....",
            "......755",
            "...$.*...",
            ".664.598.",
        ).day3part1() shouldBe 4361
    }

    "We can solve the example part 2" {
        flowOf(
            "467..114..",
            "...*......",
            "..35..633.",
            "......#...",
            "617*......",
            ".....+.58.",
            "..592.....",
            "......755.",
            "...$.*....",
            ".664.598..",
        ).day3part2() shouldBe 467835
    }
})
