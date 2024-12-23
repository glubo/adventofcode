package cz.glubo.adventofcode.y2024.day23

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2024/day/23
 */
class Day23Test :
    StringSpec({

        "day23 example part 1 matches" {
            y2024day23part1(
                TestInput(
                    """
                    kh-tc
                    qp-kh
                    de-cg
                    ka-co
                    yn-aq
                    qp-ub
                    cg-tb
                    vc-aq
                    tb-ka
                    wh-tc
                    yn-cg
                    kh-ub
                    ta-co
                    de-co
                    tc-td
                    tb-wq
                    wh-td
                    ta-ka
                    td-qp
                    aq-cg
                    wq-ub
                    ub-vc
                    de-ta
                    wq-aq
                    wq-vc
                    wh-yn
                    ka-de
                    kh-ta
                    co-tc
                    wh-qp
                    tb-vc
                    td-yn
                    """.trimIndent(),
                ),
            ) shouldBe 7
        }

        "day23 example part 2 matches" {
            y2024day23part2(
                TestInput(
                    """
                    kh-tc
                    qp-kh
                    de-cg
                    ka-co
                    yn-aq
                    qp-ub
                    cg-tb
                    vc-aq
                    tb-ka
                    wh-tc
                    yn-cg
                    kh-ub
                    ta-co
                    de-co
                    tc-td
                    tb-wq
                    wh-td
                    ta-ka
                    td-qp
                    aq-cg
                    wq-ub
                    ub-vc
                    de-ta
                    wq-aq
                    wq-vc
                    wh-yn
                    ka-de
                    kh-ta
                    co-tc
                    wh-qp
                    tb-vc
                    td-yn
                    """.trimIndent(),
                ),
            ) shouldBe "co,de,ka,ta"
        }
    })
