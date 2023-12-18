package cz.glubo.adventofcode.day16

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/16
 */
@Suppress("UNUSED")
class Day16Test : FreeSpec({
    "We can solve simplified example" {
        flowOf(
            ".|....",
            "..../.",
            ".-.-/.",
        ).day16part1() shouldBe 10
    }

    "We can solve the example" {
        flowOf(
            ".|...\\....",
            "|.-.\\.....",
            ".....|-...",
            "........|.",
            "..........",
            ".........\\",
            "..../.\\\\..",
            ".-.-/..|..",
            ".|....-|.\\",
            "..//.|....",
        ).day16part1() shouldBe 46
    }

    "We can solve the example part 2" {
        flowOf(
            ".|...\\....",
            "|.-.\\.....",
            ".....|-...",
            "........|.",
            "..........",
            ".........\\",
            "..../.\\\\..",
            ".-.-/..|..",
            ".|....-|.\\",
            "..//.|....",
        ).day16part2() shouldBe 51
    }
})
