package cz.glubo.adventofcode.day17

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/17
 */
@Suppress("UNUSED")
class Day17Test : FreeSpec({
    "smaller example" {
        flowOf(
            "241311323",
            "321535623",
            "344845452",
            "122865563",
            "344845452",
            "122865563",
            "254887735",
            "432655533",
        ).day17part1() shouldBe 53
    }

    "We can solve the example" {
        flowOf(
            "2413432311323",
            "3215453535623",
            "3255245654254",
            "3446585845452",
            "4546657867536",
            "1438598798454",
            "4457876987766",
            "3637877979653",
            "4654967986887",
            "4564679986453",
            "1224686865563",
            "2546548887735",
            "4322674655533",
        ).day17part1() shouldBe 102
    }

    "We can solve the example part 2" {
        flowOf(
            "2413432311323",
            "3215453535623",
            "3255245654254",
            "3446585845452",
            "4546657867536",
            "1438598798454",
            "4457876987766",
            "3637877979653",
            "4654967986887",
            "4564679986453",
            "1224686865563",
            "2546548887735",
            "4322674655533",
        ).day17part2() shouldBe 94
    }

    "We can solve the example 9 part 2" {
        flowOf(
            "111111111111",
            "999999999991",
            "999999999991",
            "999999999991",
            "999999999991",
        ).day17part2() shouldBe 71
    }
})
