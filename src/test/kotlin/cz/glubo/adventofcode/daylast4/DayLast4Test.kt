
package cz.glubo.adventofcode.daylast4

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2022/day/4
 */
@Suppress("UNUSED")
class DayLast4Test : FreeSpec({
    "We can solve the example" {
        flowOf(
            "2-4,6-8",
            "2-3,4-5",
            "5-7,7-9",
            "2-8,3-7",
            "6-6,4-6",
            "2-6,4-8",
        ).dayLast4part1() shouldBe 2
    }

    "We can solve the example part 2" {
        flowOf(
            "2-4,6-8",
            "2-3,4-5",
            "5-7,7-9",
            "2-8,3-7",
            "6-6,4-6",
            "2-6,4-8",
        ).dayLast4part2() shouldBe 4
    }
})
