package cz.glubo.adventofcode.y2023.day3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/3
 */
@Suppress("UNUSED")
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
        ).y2023day3part1() shouldBe 4361
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
        ).y2023day3part1() shouldBe 4361
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
        ).y2023day3part2() shouldBe 467835
    }
})
