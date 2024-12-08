package cz.glubo.adventofcode.utils.input

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class TestInput : Input {
    private val flow: Flow<String>

    override fun lineFlow(): Flow<String> = flow

    constructor(flow: Flow<String>) {
        this.flow = flow
    }

    constructor(multilineString: String) {
        this.flow =
            multilineString
                .split(System.lineSeparator())
                .asFlow()
    }
}
