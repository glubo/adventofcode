package cz.glubo.adventofcode

import org.junit.jupiter.api.Test

class Day1Test {
    @Test
    fun `ElfChooser can accept Elf`(){
        val elfChooser = ElfChooser()
        elfChooser.pushElf(
            Elf(emptyList())
        )
    }

}