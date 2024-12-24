package cz.glubo.adventofcode.utils

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize

class AllOrdersTest :
    FreeSpec({
        "empty" {
            val result =
                emptySet<Int>()
                    .allOrders()
                    .toList()
            result shouldHaveSize 1
            result[0] shouldHaveSize 0
        }

        "one" {
            val result =
                setOf(1)
                    .allOrders()
                    .toList()
            result shouldHaveSize 1
            result shouldContain listOf(1)
        }
        "two" {
            val result =
                setOf(1, 2)
                    .allOrders()
                    .toList()
            result shouldHaveSize 2
            result shouldContain listOf(1, 2)
            result shouldContain listOf(2, 1)
        }
        "three" {
            val result =
                setOf(1, 2, 3)
                    .allOrders()
                    .toList()
            result shouldHaveSize 6
            result shouldContain listOf(1, 2, 3)
            result shouldContain listOf(1, 3, 2)
            result shouldContain listOf(2, 1, 3)
            result shouldContain listOf(2, 3, 1)
            result shouldContain listOf(3, 1, 2)
            result shouldContain listOf(3, 2, 1)
        }
    })
