package cz.glubo.adventofcode.utils

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize

class AllSubsetsTest :
    FreeSpec({
        "empty" {
            setOf<Int>().allSubsets().toList() shouldHaveSize 0
        }

        "single" {
            val res = setOf(1).allSubsets().toList()
            res shouldHaveSize 1
            res shouldContain setOf(1)
        }

        "two" {
            val res = setOf(1, 2).allSubsets().toList()
            res shouldHaveSize 3
            res shouldContainAll (
                listOf(
                    setOf(1),
                    setOf(1, 2),
                    setOf(2),
                )
            )
        }
        "three" {
            val res = setOf(1, 2, 3).allSubsets().toList()
            res shouldHaveSize 7
            res shouldContainAll (
                listOf(
                    setOf(1),
                    setOf(1, 2),
                    setOf(1, 2, 3),
                    setOf(1, 3),
                    setOf(2),
                    setOf(2, 3),
                    setOf(3),
                )
            )
        }
    })
