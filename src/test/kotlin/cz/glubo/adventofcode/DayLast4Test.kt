@file:OptIn(ExperimentalCoroutinesApi::class)

package cz.glubo.adventofcode.day2

import cz.glubo.adventofcode.day4.day4part1
import cz.glubo.adventofcode.day4.day4part2
import cz.glubo.adventofcode.day4.pow2
import cz.glubo.adventofcode.daylast4.dayLast4part1
import cz.glubo.adventofcode.daylast4.dayLast4part2
import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2022/day/4
 */
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
