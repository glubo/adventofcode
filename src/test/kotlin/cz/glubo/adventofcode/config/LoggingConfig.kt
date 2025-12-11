package cz.glubo.adventofcode.config

import io.klogging.Level
import io.klogging.config.ANSI_CONSOLE
import io.klogging.config.loggingConfiguration
import io.kotest.core.listeners.BeforeProjectListener

class LoggingConfig : BeforeProjectListener {
    override suspend fun beforeProject() {
        loggingConfiguration {
            ANSI_CONSOLE()
            minDirectLogLevel(Level.DEBUG)
            logging {
                fromMinLevel(Level.DEBUG) {
                    toSink("console")
                }
            }
        }
    }
}
