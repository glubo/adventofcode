package cz.glubo.adventofcode

import cz.glubo.adventofcode.day1.day1part1
import cz.glubo.adventofcode.day1.day1part2
import cz.glubo.adventofcode.day10.day10part1
import cz.glubo.adventofcode.day10.day10part2
import cz.glubo.adventofcode.day2.day2part1
import cz.glubo.adventofcode.day2.day2part2
import cz.glubo.adventofcode.day3.day3part1
import cz.glubo.adventofcode.day3.day3part2
import cz.glubo.adventofcode.day4.day4part1
import cz.glubo.adventofcode.day4.day4part2
import cz.glubo.adventofcode.day5.day5part1
import cz.glubo.adventofcode.day5.day5part2
import cz.glubo.adventofcode.day6.day6part1
import cz.glubo.adventofcode.day6.day6part2
import cz.glubo.adventofcode.day7.day7part1
import cz.glubo.adventofcode.day7.day7part2
import cz.glubo.adventofcode.day8.day8part1
import cz.glubo.adventofcode.day8.day8part2
import cz.glubo.adventofcode.day9.day9part1
import cz.glubo.adventofcode.day9.day9part2
import cz.glubo.adventofcode.daylast4.dayLast4part1
import cz.glubo.adventofcode.daylast4.dayLast4part2
import io.klogging.Level
import io.klogging.config.ANSI_CONSOLE
import io.klogging.config.loggingConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import picocli.CommandLine
import picocli.CommandLine.Command
import java.util.concurrent.Callable
import kotlin.system.exitProcess

abstract class FlowCommand<ResultType> : Callable<Int> {
    @CommandLine.Option(names = ["--verbose", "-v"], negatable = true)
    var verbose: Boolean = false

    abstract suspend fun execute(linesFlow: Flow<String>): ResultType

    override fun call(): Int {
        runBlocking {
            val linesFlow = generateSequence(::readLine).asFlow()
            val result = execute(linesFlow)
            println(result)
        }
        return 0
    }
}

@Command(mixinStandardHelpOptions = true)
class LinesToIntCommand(
    private val action: suspend (Flow<String>) -> Int,
) : FlowCommand<Int>() {
    override suspend fun execute(linesFlow: Flow<String>) = action(linesFlow)
}

@Command(mixinStandardHelpOptions = true)
class LinesToLongCommand(
    private val action: suspend (Flow<String>) -> Long,
) : FlowCommand<Long>() {
    override suspend fun execute(linesFlow: Flow<String>) = action(linesFlow)
}

@Command(mixinStandardHelpOptions = true)
class MyHelpCommand : Callable<Int> {
    @CommandLine.Spec
    lateinit var spec: CommandLine.Model.CommandSpec

    override fun call(): Int {
        spec.commandLine().usage(System.out)
        return 0
    }
}

fun main(args: Array<String>) {
    val verbose = args.any { it in listOf("-v", "--verbose") }
    loggingConfiguration {
        ANSI_CONSOLE()
        minDirectLogLevel(Level.DEBUG)
        logging {
            if (verbose) {
                fromMinLevel(Level.DEBUG) {
                    toSink("console")
                }
            }
        }
    }
    val commands =
        mapOf(
            "day1p1" to LinesToIntCommand { it.day1part1() },
            "day1p2" to LinesToIntCommand { it.day1part2() },
            "day2p1" to LinesToIntCommand { it.day2part1() },
            "day2p2" to LinesToIntCommand { it.day2part2() },
            "day3p1" to LinesToIntCommand { it.day3part1() },
            "day3p2" to LinesToIntCommand { it.day3part2() },
            "day4p1" to LinesToIntCommand { it.day4part1() },
            "day4p2" to LinesToIntCommand { it.day4part2() },
            "daylast4p1" to LinesToIntCommand { it.dayLast4part1() },
            "daylast4p2" to LinesToIntCommand { it.dayLast4part2() },
            "day5p1" to LinesToIntCommand { it.day5part1() },
            "day5p2" to LinesToIntCommand { it.day5part2() },
            "day6p1" to LinesToIntCommand { it.day6part1() },
            "day6p2" to LinesToIntCommand { it.day6part2() },
            "day7p1" to LinesToIntCommand { it.day7part1() },
            "day7p2" to LinesToIntCommand { it.day7part2() },
            "day8p1" to LinesToIntCommand { it.day8part1() },
            "day8p2" to LinesToLongCommand { it.day8part2() },
            "day9p1" to LinesToIntCommand { it.day9part1() },
            "day9p2" to LinesToIntCommand { it.day9part2() },
            "day10p1" to LinesToLongCommand { it.day10part1() },
            "day10p2" to LinesToLongCommand { it.day10part2() },
        )

    val cmd = CommandLine(MyHelpCommand())
    commands.forEach { cmd.addSubcommand(it.key, it.value) }
    val exitCode = cmd.execute(*args)
    exitProcess(exitCode)
}
