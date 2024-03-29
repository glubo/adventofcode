package cz.glubo.adventofcode

import cz.glubo.adventofcode.day1.day1part1
import cz.glubo.adventofcode.day1.day1part2
import cz.glubo.adventofcode.day10.day10part1
import cz.glubo.adventofcode.day10.day10part2
import cz.glubo.adventofcode.day11.day11part1
import cz.glubo.adventofcode.day11.day11part2
import cz.glubo.adventofcode.day12.day12part1
import cz.glubo.adventofcode.day12.day12part2
import cz.glubo.adventofcode.day13.day13part1
import cz.glubo.adventofcode.day13.day13part2
import cz.glubo.adventofcode.day14.day14part1
import cz.glubo.adventofcode.day14.day14part2
import cz.glubo.adventofcode.day15.day15part1
import cz.glubo.adventofcode.day15.day15part2
import cz.glubo.adventofcode.day16.day16part1
import cz.glubo.adventofcode.day16.day16part2
import cz.glubo.adventofcode.day17.day17part1
import cz.glubo.adventofcode.day17.day17part2
import cz.glubo.adventofcode.day18.day18part1
import cz.glubo.adventofcode.day18.day18part2
import cz.glubo.adventofcode.day19.day19part1
import cz.glubo.adventofcode.day19.day19part2
import cz.glubo.adventofcode.day2.day2part1
import cz.glubo.adventofcode.day2.day2part2
import cz.glubo.adventofcode.day25.day25part1
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
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import picocli.CommandLine
import picocli.CommandLine.Command
import java.util.concurrent.Callable
import kotlin.system.exitProcess
import kotlin.time.measureTime

private val logger = noCoLogger({}.javaClass.`package`.toString())

abstract class FlowCommand<ResultType> : Callable<Int> {
    @CommandLine.Option(names = ["--verbose", "-v"], negatable = true)
    var verbose: Boolean = false

    abstract suspend fun execute(linesFlow: Flow<String>): ResultType

    override fun call(): Int {
        var result: ResultType?
        val duration =
            measureTime {
                runBlocking {
                    val linesFlow = generateSequence(::readLine).asFlow()
                    result = execute(linesFlow)
                }
            }
        logger.info { "result: $result (took $duration)" }
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
            "day11p1" to LinesToLongCommand { it.day11part1() },
            "day11p2" to LinesToLongCommand { it.day11part2() },
            "day12p1" to LinesToLongCommand { it.day12part1() },
            "day12p2" to LinesToLongCommand { it.day12part2() },
            "day13p1" to LinesToLongCommand { it.day13part1() },
            "day13p2" to LinesToLongCommand { it.day13part2() },
            "day14p1" to LinesToLongCommand { it.day14part1() },
            "day14p2" to LinesToLongCommand { it.day14part2() },
            "day15p1" to LinesToLongCommand { it.day15part1() },
            "day15p2" to LinesToLongCommand { it.day15part2() },
            "day16p1" to LinesToLongCommand { it.day16part1() },
            "day16p2" to LinesToLongCommand { it.day16part2() },
            "day17p1" to LinesToLongCommand { it.day17part1() },
            "day17p2" to LinesToLongCommand { it.day17part2() },
            "day18p1" to LinesToLongCommand { it.day18part1() },
            "day18p2" to LinesToLongCommand { it.day18part2() },
            "day19p1" to LinesToLongCommand { it.day19part1() },
            "day19p2" to LinesToLongCommand { it.day19part2() },
            "day25p1" to LinesToLongCommand { it.day25part1() },
        )

    val cmd = CommandLine(MyHelpCommand())
    commands.forEach { cmd.addSubcommand(it.key, it.value) }
    val exitCode = cmd.execute(*args)
    exitProcess(exitCode)
}
