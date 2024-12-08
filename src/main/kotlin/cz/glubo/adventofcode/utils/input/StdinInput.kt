package cz.glubo.adventofcode.utils.input

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class StdinInput : Input {
    override fun lineFlow(): Flow<String> =
        generateSequence(::readLine)
            .asFlow()
}
