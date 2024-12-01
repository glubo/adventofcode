package cz.glubo.adventofcode.y2023.day11

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/11
 */
@Suppress("UNUSED")
class Day11Test : FreeSpec({
    "We can solve the example" {
        flowOf(
            "...#......",
            ".......#..",
            "#.........",
            "..........",
            "......#...",
            ".#........",
            ".........#",
            "..........",
            ".......#..",
            "#...#.....",
        ).y2023day11part1() shouldBe 374
    }

    "We can solve the example part 2 factor 10" {
        flowOf(
            "...#......",
            ".......#..",
            "#.........",
            "..........",
            "......#...",
            ".#........",
            ".........#",
            "..........",
            ".......#..",
            "#...#.....",
        ).sumOfDistances(10) shouldBe 1030
    }

    "We can solve the example part 2 factor 100" {
        flowOf(
            "...#......",
            ".......#..",
            "#.........",
            "..........",
            "......#...",
            ".#........",
            ".........#",
            "..........",
            ".......#..",
            "#...#.....",
        ).sumOfDistances(100) shouldBe 8410
    }
})
