package cz.glubo.adventofcode.y2023.day7

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/7
 */
@Suppress("UNUSED")
class Day7Test : FreeSpec({
    "We can solve the example" {
        flowOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483",
        ).y2023day7part1() shouldBe 6440
    }

    "We can solve the example part 2" {
        flowOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483",
        ).y2023day7part2() shouldBe 5905
    }
})
