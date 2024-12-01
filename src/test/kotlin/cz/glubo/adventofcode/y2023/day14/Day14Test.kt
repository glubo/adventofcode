package cz.glubo.adventofcode.y2023.day14

import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/14
 */
@Suppress("UNUSED")
class Day14Test : FreeSpec({
    "We can solve the example" {
        flowOf(
            "O....#....",
            "O.OO#....#",
            ".....##...",
            "OO.#O....O",
            ".O.....O#.",
            "O.#..O.#.#",
            "..O..#O..O",
            ".......O..",
            "#....###..",
            "#OO..#....",
        ).y2023day14part1() shouldBe 136
    }
    "We can solve the examplee" {
        flowOf(
            ".",
            "O",
            ".",
            ".",
            ".",
            "#",
            "O",
            ".",
            ".",
            "O",
        ).y2023day14part1() shouldBe 10 + 7
    }

    "We can solve the example part 2" {
        flowOf(
            "O....#....",
            "O.OO#....#",
            ".....##...",
            "OO.#O....O",
            ".O.....O#.",
            "O.#..O.#.#",
            "..O..#O..O",
            ".......O..",
            "#....###..",
            "#OO..#....",
        ).y2023day14part2() shouldBe 64
    }

    "We can rotate" - {
        withData(
            (0 to 0) to (2 to 0),
            (0 to 0) to (2 to 0),
            (1 to 1) to (1 to 1),
            (1 to 2) to (0 to 1),
        ) {
            it.first.rotate(3) shouldBe it.second
        }
//            "123"
//            "456"
//            "789"
//        to
//            "741"
//            "852"
//            "963"
    }

    "We can tilt up" {
        Mirror(
            3,
            listOf(
                0 to 0,
                0 to 2,
                1 to 2,
                2 to 2,
            ),
            listOf(
                1 to 1,
                2 to 0,
            ),
        ).tiltUp() shouldBe
                Mirror(
                    3,
                    listOf(
                        0 to 0,
                        0 to 1,
                        1 to 2,
                        2 to 1,
                    ),
                    listOf(
                        1 to 1,
                        2 to 0,
                    ),
                )
    }

    "We can parse" {
        flowOf(
            "#.O",
            "...",
            "O.#",
        ).toMirror() shouldBe
                Mirror(
                    3,
                    listOf(0 to 2, 2 to 0),
                    listOf(0 to 0, 2 to 2),
                )
    }

    "We can weight" {
        Mirror(
            3,
            listOf(0 to 2, 2 to 0),
            listOf(0 to 0, 2 to 2),
        ).weight() shouldBe 4
    }
})
