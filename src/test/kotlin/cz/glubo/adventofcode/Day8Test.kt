@file:OptIn(ExperimentalCoroutinesApi::class)

package cz.glubo.adventofcode.day2

import cz.glubo.adventofcode.day8.day8part1
import cz.glubo.adventofcode.day8.day8part2
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/7
 */
class Day8Test : FreeSpec({
    "We can solve the example" {
        flowOf(
            "RL",
            "",
            "AAA = (BBB, CCC)",
            "BBB = (DDD, EEE)",
            "CCC = (ZZZ, GGG)",
            "DDD = (DDD, DDD)",
            "EEE = (EEE, EEE)",
            "GGG = (GGG, GGG)",
            "ZZZ = (ZZZ, ZZZ)",
        ).day8part1() shouldBe 2
    }
    "We can solve the second example" {
        flowOf(
            "LLR",
            "",
            "AAA = (BBB, BBB)",
            "BBB = (AAA, ZZZ)",
            "ZZZ = (ZZZ, ZZZ)",
        ).day8part1() shouldBe 6
    }

    "We can solve the example part 2" {
        flowOf(
            "LR",
            "",
            "11A = (11B, XXX)",
            "11B = (XXX, 11Z)",
            "11Z = (11B, XXX)",
            "22A = (22B, XXX)",
            "22B = (22C, 22C)",
            "22C = (22Z, 22Z)",
            "22Z = (22B, 22B)",
            "XXX = (XXX, XXX)",
        ).day8part2() shouldBe 6
    }
})
