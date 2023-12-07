@file:OptIn(ExperimentalCoroutinesApi::class)

package cz.glubo.adventofcode.day2

import cz.glubo.adventofcode.day4.day4part1
import cz.glubo.adventofcode.day4.day4part2
import cz.glubo.adventofcode.day4.pow2
import cz.glubo.adventofcode.day5.MapElement
import cz.glubo.adventofcode.day5.day5part1
import cz.glubo.adventofcode.day5.day5part2
import cz.glubo.adventofcode.day6.day6part1
import cz.glubo.adventofcode.day6.day6part2
import cz.glubo.adventofcode.day7.day7part1
import cz.glubo.adventofcode.day7.day7part2
import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/7
 */
class Day7Test : FreeSpec({
    "We can solve the example" {
        flowOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483",
        ).day7part1() shouldBe 6440
    }


    "We can solve the example part 2" {
        flowOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483",
        ).day7part2() shouldBe 5905
    }
})
