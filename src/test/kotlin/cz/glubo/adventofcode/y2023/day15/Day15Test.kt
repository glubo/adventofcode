package cz.glubo.adventofcode.y2023.day15

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/15
 */
@Suppress("UNUSED")
class Day15Test : FreeSpec({
    "We can solve the example" {
        flowOf(
            "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7",
        ).y2023day15part1() shouldBe 1320
    }

    "We can solve the example part 2" {
        flowOf(
            "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7",
        ).y2023day15part2() shouldBe 145
    }
})
