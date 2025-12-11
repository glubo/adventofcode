package cz.glubo.adventofcode.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension

object ProjectConfig : AbstractProjectConfig() {
    override val extensions: List<Extension>
        get() = listOf(LoggingConfig())
}