package cz.glubo.adventofcode.y2023.day6

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/6
 */
@Suppress("UNUSED")
class Day6Test :
    FreeSpec({
        "We can solve the example" {
            flowOf(
                "Time:      7  15   30",
                "Distance:  9  40  200",
            ).y2023day6part1() shouldBe 288
        }

        "We can solve the example part 2" {
            flowOf(
                "Time:      7  15   30",
                "Distance:  9  40  200",
            ).y2023day6part2() shouldBe 71503
        }
    })
