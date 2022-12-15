import cz.glubo.adventofcode.day1.day1part1
import cz.glubo.adventofcode.day1.day1part2
import cz.glubo.adventofcode.day2.day2part1
import cz.glubo.adventofcode.day2.day2part2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import picocli.CommandLine
import picocli.CommandLine.Command
import java.util.concurrent.Callable
import kotlin.system.exitProcess

abstract class FlowCommand<ResultType> : Callable<Int> {
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
class Day1Part1Command : FlowCommand<Int>() {
    override suspend fun execute(linesFlow: Flow<String>) = linesFlow.day1part1()
}

@Command(mixinStandardHelpOptions = true)
class Day1Part2Command : FlowCommand<Int>() {
    override suspend fun execute(linesFlow: Flow<String>) = linesFlow.day1part2()
}

@Command(mixinStandardHelpOptions = true)
class Day2Part1Command : FlowCommand<Int>() {
    override suspend fun execute(linesFlow: Flow<String>) = linesFlow.day2part1()
}

@Command(mixinStandardHelpOptions = true)
class Day2Part2Command : FlowCommand<Int>() {
    override suspend fun execute(linesFlow: Flow<String>) = linesFlow.day2part2()
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
    val commands = mapOf(
        "day1p1" to Day1Part1Command(),
        "day1p2" to Day1Part2Command(),
        "day2p1" to Day2Part1Command(),
        "day2p2" to Day2Part2Command(),
    )

    val cmd = CommandLine(MyHelpCommand())
    commands.forEach { cmd.addSubcommand(it.key, it.value) }
    val exitCode = cmd.execute(*args)
    exitProcess(exitCode)
}