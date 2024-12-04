package cz.glubo.adventofcode.y2024.day4

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2024/day/4
 */
@Suppress("UNUSED")
class Day4Test : StringSpec({

    "day4 example part 1 matches" {
        y2024day4part1(
            flowOf(
                "..X...",
                ".SAMX.",
                ".A..A.",
                "XMAS.S",
                ".X....",
            )
        ) shouldBe 4
    }

    "day4 example 2 part 1 matches" {
        y2024day4part1(
            flowOf(
                "MMMSXXMASM",
                "MSAMXMSMSA",
                "AMXSXMAAMM",
                "MSAMASMSMX",
                "XMASAMXAMM",
                "XXAMMXXAMA",
                "SMSMSASXSS",
                "SAXAMASAAA",
                "MAMMMXMMMM",
                "MXMXAXMASX",
            )
        ) shouldBe 18
    }

    "day4 example part 2 matches" {
        y2024day4part2(
            flowOf(
                ".M.S......",
                "..A..MSMS.",
                ".M.S.MAA..",
                "..A.ASMSM.",
                ".M.S.M....",
                "..........",
                "S.S.S.S.S.",
                ".A.A.A.A..",
                "M.M.M.M.M.",
                "..........",
            )
        ) shouldBe 9
    }

})
