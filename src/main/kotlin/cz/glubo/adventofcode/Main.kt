import cz.glubo.adventofcode.day1.day1p1
import cz.glubo.adventofcode.day1.day1p2
import cz.glubo.adventofcode.day2.day2
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
class Day1P1Command : FlowCommand<Int>() {
    override suspend fun execute(linesFlow: Flow<String>) = linesFlow.day1p1()
}

@Command(mixinStandardHelpOptions = true)
class Day1P2Command : FlowCommand<Int>() {
    override suspend fun execute(linesFlow: Flow<String>) = linesFlow.day1p2()
}

@Command(mixinStandardHelpOptions = true)
class Day2Command : FlowCommand<Int>() {
    override suspend fun execute(linesFlow: Flow<String>) = linesFlow.day2()
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
        "day1p1" to Day1P1Command(),
        "day1p2" to Day1P2Command(),
        "day2" to Day2Command(),
    )

    val cmd = CommandLine(MyHelpCommand())
    commands.forEach { cmd.addSubcommand(it.key, it.value) }
    val exitCode = cmd.execute(*args)
    exitProcess(exitCode)
}