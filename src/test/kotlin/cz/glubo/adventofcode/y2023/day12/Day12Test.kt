package cz.glubo.adventofcode.y2023.day12

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/12
 */
@Suppress("UNUSED")
class Day12Test :
    FreeSpec({
        "We can solve the example" {
            flowOf(
                "#.#.### 1,1,3",
                ".#...#....###. 1,1,3",
                ".#.###.#.###### 1,3,1,6",
                "####.#...#... 4,1,1",
                "#....######..#####. 1,6,5",
                ".###.##....# 3,2,1",
            ).y2023day12part1() shouldBe 6
        }

        "We can solve the example 1" {
            flowOf(
                "???.### 1,1,3",
            ).y2023day12part1() shouldBe 1
        }

        "We can solve the example 2" {
            flowOf(
                ".??..??...?##. 1,1,3",
            ).y2023day12part1() shouldBe 4
        }

        "We can solve the example 3" {
            flowOf(
                ".??..??...?##. 1,1,3",
            ).y2023day12part1() shouldBe 4
        }

        "We can solve the example 4.1" {
            flowOf(
                "#?#?#? 6",
            ).y2023day12part1() shouldBe 1
        }

        "We can solve the example 4" {
            flowOf(
                "?#?#?#?#?#?#?#? 1,3,1,6",
                //   ".#.###.#.###### 1,3,1,6",
            ).y2023day12part1() shouldBe 1
        }
        "We can solve the example 5" {
            flowOf(
                "????.#...#... 4,1,1",
            ).y2023day12part1() shouldBe 1
        }
        "We can solve the example 6" {
            flowOf(
                "????.######..#####. 1,6,5",
            ).y2023day12part1() shouldBe 4
        }
        "We can solve the example 7" {
            flowOf(
                "?###???????? 3,2,1",
            ).y2023day12part1() shouldBe 10
        }

        "We can solve the example complete part 1" {
            flowOf(
                "???.### 1,1,3",
                ".??..??...?##. 1,1,3",
                "?#?#?#?#?#?#?#? 1,3,1,6",
                "????.#...#... 4,1,1",
                "????.######..#####. 1,6,5",
                "?###???????? 3,2,1",
            ).y2023day12part1() shouldBe 21
        }

        "We can solve the example part 2" {
            flowOf(
                "???.### 1,1,3",
                ".??..??...?##. 1,1,3",
                "?#?#?#?#?#?#?#? 1,3,1,6",
                "????.#...#... 4,1,1",
                "????.######..#####. 1,6,5",
                "?###???????? 3,2,1",
            ).y2023day12part2() shouldBe 525152
        }
    })
