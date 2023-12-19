package cz.glubo.adventofcode.utils

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class RangesTest : FreeSpec({
    "rangeUnion single" - {
        "two disjoint ranges" {
            (1..2) rangeUnion (4..5) shouldBe listOf((1..2), (4..5))
        }
        "two touching ranges" {
            (1..2) rangeUnion (3..4) shouldBe listOf((1..4))
        }
        "two overlapping ranges" {
            (1..3) rangeUnion (3..4) shouldBe listOf((1..4))
        }
        "two overlapping ranges flipped" {
            (3..4) rangeUnion (1..3) shouldBe listOf((1..4))
        }
        "one inside the other" {
            (1..4) rangeUnion (2..3) shouldBe listOf((1..4))
        }
        "one inside the other flipped" {
            (2..3) rangeUnion (1..4) shouldBe listOf((1..4))
        }
    }

    "rangeUnion list" - {
        "two disjoint ranges" {
            listOf((1..2), (4..5)).rangeUnion() shouldBe listOf((1..2), (4..5))
        }
        "three disjoint ranges" {
            listOf((1..2), (4..5), (7..8)).rangeUnion() shouldBe listOf((1..2), (4..5), (7..8))
        }
        "three touching ranges" {
            listOf((1..<4), (4..6), (7..8)).rangeUnion() shouldBe listOf((1..8))
        }
    }

    "split range single" - {
        "simple split" {
            (1..5).split(4) shouldBe ((1..3) to(4..5))
        }
        "split on start" {
            (1..5).split(1) shouldBe (null to (1..5))
        }
        "split before start" {
            (1..5).split(-1) shouldBe (null to (1..5))
        }
        "split at the end" {
            (1..5).split(5) shouldBe ((1..4) to (5..5))
        }
        "split after the end" {
            (1..5).split(6) shouldBe ((1..5) to null)
        }
    }
})
