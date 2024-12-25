package cz.glubo.adventofcode.utils

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class GetBitTest :
    FreeSpec({
        "examples" {
            assertSoftly {
                1L.getBit(0) shouldBe 1
                3L.getBit(0) shouldBe 1
                1025L.getBit(0) shouldBe 1

                2L.getBit(1) shouldBe 1
                2L.getBit(0) shouldBe 0

                1024L.getBit(10) shouldBe 1
                1024L.getBit(0) shouldBe 0
            }
        }

        "bitDistance" {
            assertSoftly {
                1L.bitDistance(1L) shouldBe 0
                2L.bitDistance(0L) shouldBe 1
                2L.bitDistance(1L) shouldBe 2
                1024L.bitDistance(1L) shouldBe 2
                1L.bitDistance(1024L) shouldBe 2
            }
        }
    })
