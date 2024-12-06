package cz.glubo.adventofcode.y2023.day10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/10
 */
@Suppress("UNUSED")
class Day10Test :
    FreeSpec({
        "We can solve the example" {
            flowOf(
                ".....",
                ".F-7.",
                ".S.|.",
                ".L-J.",
                ".....",
            ).y2023day10part1() shouldBe 4
        }

        "We can solve the example with extra fields" {
            flowOf(
                "-L|F7",
                "7S-7|",
                "L|7||",
                "-L-J|",
                "L|-JF",
            ).y2023day10part1() shouldBe 4
        }

        "We can solve the harder example" {
            flowOf(
                "..F7.",
                ".FJ|.",
                "SJ.L7",
                "|F--J",
                "LJ...",
            ).y2023day10part1() shouldBe 8
        }

        "We can solve the simple example part 2" {
            flowOf(
                "F7",
                "SJ",
            ).y2023day10part2() shouldBe 0
        }

        "We can solve the example with extra fields part 2" {
            flowOf(
                "-L|F7",
                "7S-7|",
                "L|7||",
                "-L-J|",
                "L|-JF",
            ).y2023day10part1() shouldBe 4
        }

        "We can solve the example part 2" {
            flowOf(
                "...........",
                ".S-------7.",
                ".|F-----7|.",
                ".||.....||.",
                ".||.....||.",
                ".|L-7.F-J|.",
                ".|..|.|..|.",
                ".L--J.L--J.",
                "...........",
            ).y2023day10part2() shouldBe 4
        }
    })
