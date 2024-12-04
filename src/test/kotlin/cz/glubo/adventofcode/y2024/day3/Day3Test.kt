package cz.glubo.adventofcode.y2024.day3

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/1
 */
@Suppress("UNUSED")
class Day3Test : StringSpec({

    "day3 example part 1 matches" {
        y2024day3part1(
            flowOf(
                "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))",
            )
        ) shouldBe 161
    }

    "day3 example part 2 matches" {
        y2024day3part2(
            flowOf(
                "don't()do()xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
            )
        ) shouldBe 48
    }

})
