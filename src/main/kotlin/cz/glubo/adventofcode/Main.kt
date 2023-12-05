import cz.glubo.adventofcode.day1.day1part1
import cz.glubo.adventofcode.day1.day1part2
import cz.glubo.adventofcode.day2.day2part1
import cz.glubo.adventofcode.day2.day2part2
import cz.glubo.adventofcode.day3.day3part1
import cz.glubo.adventofcode.day3.day3part2
import cz.glubo.adventofcode.day4.day4part1
import cz.glubo.adventofcode.day4.day4part2
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
class LinesToIntCommand(
    private val action: suspend (Flow<String>) -> Int,
) : FlowCommand<Int>() {
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
        )

    val cmd = CommandLine(MyHelpCommand())
    commands.forEach { cmd.addSubcommand(it.key, it.value) }
    val exitCode = cmd.execute(*args)
    exitProcess(exitCode)
}
