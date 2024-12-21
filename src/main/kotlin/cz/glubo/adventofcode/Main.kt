package cz.glubo.adventofcode

import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.input.Input
import cz.glubo.adventofcode.utils.input.StdinInput
import cz.glubo.adventofcode.y2023.day1.y2023day1part1
import cz.glubo.adventofcode.y2023.day1.y2023day1part2
import cz.glubo.adventofcode.y2023.day10.y2023day10part1
import cz.glubo.adventofcode.y2023.day10.y2023day10part2
import cz.glubo.adventofcode.y2023.day11.y2023day11part1
import cz.glubo.adventofcode.y2023.day11.y2023day11part2
import cz.glubo.adventofcode.y2023.day12.y2023day12part1
import cz.glubo.adventofcode.y2023.day12.y2023day12part2
import cz.glubo.adventofcode.y2023.day13.y2023day13part1
import cz.glubo.adventofcode.y2023.day13.y2023day13part2
import cz.glubo.adventofcode.y2023.day14.y2023day14part1
import cz.glubo.adventofcode.y2023.day14.y2023day14part2
import cz.glubo.adventofcode.y2023.day15.y2023day15part1
import cz.glubo.adventofcode.y2023.day15.y2023day15part2
import cz.glubo.adventofcode.y2023.day16.y2023day16part1
import cz.glubo.adventofcode.y2023.day16.y2023day16part2
import cz.glubo.adventofcode.y2023.day17.y2023day17part1
import cz.glubo.adventofcode.y2023.day17.y2023day17part2
import cz.glubo.adventofcode.y2023.day18.y2023day18part1
import cz.glubo.adventofcode.y2023.day18.y2023day18part2
import cz.glubo.adventofcode.y2023.day19.y2023day19part1
import cz.glubo.adventofcode.y2023.day19.y2023day19part2
import cz.glubo.adventofcode.y2023.day2.y2023day2part1
import cz.glubo.adventofcode.y2023.day2.y2023day2part2
import cz.glubo.adventofcode.y2023.day25.y2023day25part1
import cz.glubo.adventofcode.y2023.day3.y2023day3part1
import cz.glubo.adventofcode.y2023.day3.y2023day3part2
import cz.glubo.adventofcode.y2023.day4.y2023day4part1
import cz.glubo.adventofcode.y2023.day4.y2023day4part2
import cz.glubo.adventofcode.y2023.day5.y2023day5part1
import cz.glubo.adventofcode.y2023.day5.y2023day5part2
import cz.glubo.adventofcode.y2023.day6.y2023day6part1
import cz.glubo.adventofcode.y2023.day6.y2023day6part2
import cz.glubo.adventofcode.y2023.day7.y2023day7part1
import cz.glubo.adventofcode.y2023.day7.y2023day7part2
import cz.glubo.adventofcode.y2023.day8.y2023day8part1
import cz.glubo.adventofcode.y2023.day8.y2023day8part2
import cz.glubo.adventofcode.y2023.day9.y2023day9part1
import cz.glubo.adventofcode.y2023.day9.y2023day9part2
import cz.glubo.adventofcode.y2023.daylast4.y2023dayLast4part1
import cz.glubo.adventofcode.y2023.daylast4.y2023dayLast4part2
import cz.glubo.adventofcode.y2024.day1.y2024day1part1
import cz.glubo.adventofcode.y2024.day1.y2024day1part2
import cz.glubo.adventofcode.y2024.day10.y2024day10part1
import cz.glubo.adventofcode.y2024.day10.y2024day10part2
import cz.glubo.adventofcode.y2024.day11.y2024day11part1
import cz.glubo.adventofcode.y2024.day11.y2024day11part2
import cz.glubo.adventofcode.y2024.day12.y2024day12part1
import cz.glubo.adventofcode.y2024.day12.y2024day12part2
import cz.glubo.adventofcode.y2024.day13.y2024day13part1
import cz.glubo.adventofcode.y2024.day13.y2024day13part2
import cz.glubo.adventofcode.y2024.day14.y2024day14part1
import cz.glubo.adventofcode.y2024.day14.y2024day14part2
import cz.glubo.adventofcode.y2024.day15.y2024day15part1
import cz.glubo.adventofcode.y2024.day15.y2024day15part2
import cz.glubo.adventofcode.y2024.day16.y2024day16part1
import cz.glubo.adventofcode.y2024.day16.y2024day16part2
import cz.glubo.adventofcode.y2024.day17.y2024day17part1
import cz.glubo.adventofcode.y2024.day17.y2024day17part2
import cz.glubo.adventofcode.y2024.day18.y2024day18part1
import cz.glubo.adventofcode.y2024.day18.y2024day18part2
import cz.glubo.adventofcode.y2024.day19.y2024day19part1
import cz.glubo.adventofcode.y2024.day19.y2024day19part2
import cz.glubo.adventofcode.y2024.day2.y2024day2part1
import cz.glubo.adventofcode.y2024.day2.y2024day2part2
import cz.glubo.adventofcode.y2024.day20.y2024day20part1
import cz.glubo.adventofcode.y2024.day20.y2024day20part2
import cz.glubo.adventofcode.y2024.day21.y2024day21part1
import cz.glubo.adventofcode.y2024.day21.y2024day21part2
import cz.glubo.adventofcode.y2024.day3.y2024day3part1
import cz.glubo.adventofcode.y2024.day3.y2024day3part2
import cz.glubo.adventofcode.y2024.day4.y2024day4part1
import cz.glubo.adventofcode.y2024.day4.y2024day4part2
import cz.glubo.adventofcode.y2024.day5.y2024day5part1
import cz.glubo.adventofcode.y2024.day5.y2024day5part2
import cz.glubo.adventofcode.y2024.day6.y2024day6part1
import cz.glubo.adventofcode.y2024.day6.y2024day6part2
import cz.glubo.adventofcode.y2024.day7.y2024day7part1
import cz.glubo.adventofcode.y2024.day7.y2024day7part2
import cz.glubo.adventofcode.y2024.day8.y2024day8part1
import cz.glubo.adventofcode.y2024.day8.y2024day8part2
import cz.glubo.adventofcode.y2024.day9.y2024day9part1
import cz.glubo.adventofcode.y2024.day9.y2024day9part2
import cz.glubo.adventofcode.y2024.dayN.y2024dayNpart1
import cz.glubo.adventofcode.y2024.dayN.y2024dayNpart2
import io.klogging.Level
import io.klogging.config.ANSI_CONSOLE
import io.klogging.config.loggingConfiguration
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import picocli.CommandLine
import picocli.CommandLine.Command
import java.util.concurrent.Callable
import kotlin.system.exitProcess
import kotlin.time.measureTime

private val logger = noCoLogger({}.javaClass.`package`.toString())

abstract class InputCommand<ResultType> : Callable<Int> {
    @CommandLine.Option(names = ["--verbose", "-v"], negatable = true)
    var verbose: Boolean = false

    abstract suspend fun execute(input: Input): ResultType

    override fun call(): Int {
        var result: ResultType?
        val duration =
            measureTime {
                runBlocking {
                    result = execute(StdinInput())
                }
            }
        logger.info { "result: $result (took $duration)" }
        return 0
    }
}

abstract class FlowCommand<ResultType> : Callable<Int> {
    @CommandLine.Option(names = ["--verbose", "-v"], negatable = true)
    var verbose: Boolean = false

    abstract suspend fun execute(linesFlow: Flow<String>): ResultType

    override fun call(): Int {
        var result: ResultType?
        val duration =
            measureTime {
                runBlocking {
                    result = execute(StdinInput().lineFlow())
                }
            }
        logger.info { "result: $result (took $duration)" }
        return 0
    }
}

@Command(mixinStandardHelpOptions = true)
class InputToLongCommand(
    private val action: suspend (input: Input) -> Long,
) : InputCommand<Long>() {
    override suspend fun execute(input: Input) = action(input)
}

@Command(mixinStandardHelpOptions = true)
class InputToStringCommand(
    private val action: suspend (input: Input) -> String,
) : InputCommand<String>() {
    override suspend fun execute(input: Input) = action(input)
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
            "2023day1p1" to LinesToIntCommand { it.y2023day1part1() },
            "2023day1p2" to LinesToIntCommand { it.y2023day1part2() },
            "2023day2p1" to LinesToIntCommand { it.y2023day2part1() },
            "2023day2p2" to LinesToIntCommand { it.y2023day2part2() },
            "2023day3p1" to LinesToIntCommand { it.y2023day3part1() },
            "2023day3p2" to LinesToIntCommand { it.y2023day3part2() },
            "2023day4p1" to LinesToIntCommand { it.y2023day4part1() },
            "2023day4p2" to LinesToIntCommand { it.y2023day4part2() },
            "2023daylast4p1" to LinesToIntCommand { it.y2023dayLast4part1() },
            "2023daylast4p2" to LinesToIntCommand { it.y2023dayLast4part2() },
            "2023day5p1" to LinesToIntCommand { it.y2023day5part1() },
            "2023day5p2" to LinesToIntCommand { it.y2023day5part2() },
            "2023day6p1" to LinesToIntCommand { it.y2023day6part1() },
            "2023day6p2" to LinesToIntCommand { it.y2023day6part2() },
            "2023day7p1" to LinesToIntCommand { it.y2023day7part1() },
            "2023day7p2" to LinesToIntCommand { it.y2023day7part2() },
            "2023day8p1" to LinesToIntCommand { it.y2023day8part1() },
            "2023day8p2" to LinesToLongCommand { it.y2023day8part2() },
            "2023day9p1" to LinesToIntCommand { it.y2023day9part1() },
            "2023day9p2" to LinesToIntCommand { it.y2023day9part2() },
            "2023day10p1" to LinesToLongCommand { it.y2023day10part1() },
            "2023day10p2" to LinesToLongCommand { it.y2023day10part2() },
            "2023day11p1" to LinesToLongCommand { it.y2023day11part1() },
            "2023day11p2" to LinesToLongCommand { it.y2023day11part2() },
            "2023day12p1" to LinesToLongCommand { it.y2023day12part1() },
            "2023day12p2" to LinesToLongCommand { it.y2023day12part2() },
            "2023day13p1" to LinesToLongCommand { it.y2023day13part1() },
            "2023day13p2" to LinesToLongCommand { it.y2023day13part2() },
            "2023day14p1" to LinesToLongCommand { it.y2023day14part1() },
            "2023day14p2" to LinesToLongCommand { it.y2023day14part2() },
            "2023day15p1" to LinesToLongCommand { it.y2023day15part1() },
            "2023day15p2" to LinesToLongCommand { it.y2023day15part2() },
            "2023day16p1" to LinesToLongCommand { it.y2023day16part1() },
            "2023day16p2" to LinesToLongCommand { it.y2023day16part2() },
            "2023day17p1" to LinesToLongCommand { it.y2023day17part1() },
            "2023day17p2" to LinesToLongCommand { it.y2023day17part2() },
            "2023day18p1" to LinesToLongCommand { it.y2023day18part1() },
            "2023day18p2" to LinesToLongCommand { it.y2023day18part2() },
            "2023day19p1" to LinesToLongCommand { it.y2023day19part1() },
            "2023day19p2" to LinesToLongCommand { it.y2023day19part2() },
            "2023day25p1" to LinesToLongCommand { it.y2023day25part1() },
            "2024dayNp1" to LinesToLongCommand { y2024dayNpart1(StdinInput()) },
            "2024dayNp2" to LinesToLongCommand { y2024dayNpart2(StdinInput()) },
            "2024day1p1" to LinesToLongCommand { y2024day1part1(it) },
            "2024day1p2" to LinesToLongCommand { y2024day1part2(it) },
            "2024day2p1" to LinesToLongCommand { y2024day2part1(it) },
            "2024day2p2" to LinesToLongCommand { y2024day2part2(it) },
            "2024day3p1" to LinesToLongCommand { y2024day3part1(it) },
            "2024day3p2" to LinesToLongCommand { y2024day3part2(it) },
            "2024day4p1" to LinesToLongCommand { y2024day4part1(it) },
            "2024day4p2" to LinesToLongCommand { y2024day4part2(it) },
            "2024day5p1" to LinesToLongCommand { y2024day5part1(it) },
            "2024day5p2" to LinesToLongCommand { y2024day5part2(it) },
            "2024day6p1" to LinesToLongCommand { y2024day6part1(it) },
            "2024day6p2" to LinesToLongCommand { y2024day6part2(it) },
            "2024day7p1" to LinesToLongCommand { y2024day7part1(it) },
            "2024day7p2" to LinesToLongCommand { y2024day7part2(it) },
            "2024day8p1" to InputToLongCommand { y2024day8part1(it) },
            "2024day8p2" to InputToLongCommand { y2024day8part2(it) },
            "2024day9p1" to InputToLongCommand { y2024day9part1(it) },
            "2024day9p2" to InputToLongCommand { y2024day9part2(it) },
            "2024day10p1" to InputToLongCommand { y2024day10part1(it) },
            "2024day10p2" to InputToLongCommand { y2024day10part2(it) },
            "2024day11p1" to InputToLongCommand { y2024day11part1(it) },
            "2024day11p2" to InputToLongCommand { y2024day11part2(it) },
            "2024day12p1" to InputToLongCommand { y2024day12part1(it) },
            "2024day12p2" to InputToLongCommand { y2024day12part2(it) },
            "2024day13p1" to InputToLongCommand { y2024day13part1(it) },
            "2024day13p2" to InputToLongCommand { y2024day13part2(it) },
            "2024day14p1" to InputToLongCommand { y2024day14part1(it, IVec2(101, 103)) },
            "2024day14p2" to InputToLongCommand { y2024day14part2(it, IVec2(101, 103)) },
            "2024day15p1" to InputToLongCommand { y2024day15part1(it) },
            "2024day15p2" to InputToLongCommand { y2024day15part2(it) },
            "2024day16p1" to InputToLongCommand { y2024day16part1(it) },
            "2024day16p2" to InputToLongCommand { y2024day16part2(it) },
            "2024day17p1" to InputToStringCommand { y2024day17part1(it) },
            "2024day17p2" to InputToLongCommand { y2024day17part2(it) },
            "2024day18p1" to InputToLongCommand { y2024day18part1(it, 1024, 70) },
            "2024day18p2" to InputToStringCommand { y2024day18part2(it, 1024, 70) },
            "2024day19p1" to InputToLongCommand { y2024day19part1(it) },
            "2024day19p2" to InputToLongCommand { y2024day19part2(it) },
            "2024day20p1" to InputToLongCommand { y2024day20part1(it, 100) },
            "2024day20p2" to InputToLongCommand { y2024day20part2(it, 100) },
            "2024day21p1" to InputToLongCommand { y2024day21part1(it) },
            "2024day21p2" to InputToLongCommand { y2024day21part2(it) },
        )

    val cmd = CommandLine(MyHelpCommand())
    commands.forEach { cmd.addSubcommand(it.key, it.value) }
    val exitCode = cmd.execute(*args)
    exitProcess(exitCode)
}
