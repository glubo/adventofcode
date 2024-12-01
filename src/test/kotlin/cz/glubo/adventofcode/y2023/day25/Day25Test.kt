package cz.glubo.adventofcode.y2023.day25

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2523/day/25
 */
@Suppress("UNUSED")
class Day25Test : FreeSpec({

    "We can solve our example" {
        flowOf(
            "aa: ab ac ad ba",
            "ab: aa ac ad bb",
            "ac: aa ab ad bc",
            "ad: aa ab ac",
            "ba: bb bc bd",
            "bb: ba bc bd",
            "bc: ba bb bd",
            "bd: ba bb bc",
        ).y2023day25part1() shouldBe 16
    }

    "We can find path in our example" {
        val (_, edges) =
            flowOf(
                "aa: ab ac ad ba",
                "ab: aa ac ad bb",
                "ac: aa ab ad bc",
                "ad: aa ab ac",
                "ba: bb bc bd",
                "bb: ba bc bd",
                "bc: ba bb bd",
                "bd: ba bb bc",
            ).parseGraphData()

        findPath(edges, "bd", "ac").size shouldBeGreaterThan 0
    }
    "We can solve the example" {
        flowOf(
            "jqt: rhn xhk nvd",
            "rsh: frs pzl lsr",
            "xhk: hfx",
            "cmg: qnr nvd lhk bvb",
            "rhn: xhk bvb hfx",
            "bvb: xhk hfx",
            "pzl: lsr hfx nvd",
            "qnr: nvd",
            "ntq: jqt hfx bvb xhk",
            "nvd: lhk",
            "lsr: lhk",
            "rzs: qnr cmg lsr rsh",
            "frs: qnr lhk lsr",
        ).y2023day25part1() shouldBe 54
    }
})
