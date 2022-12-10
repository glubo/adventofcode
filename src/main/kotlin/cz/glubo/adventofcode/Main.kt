import cz.glubo.adventofcode.day1.day1
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import picocli.CommandLine
import picocli.CommandLine.Command
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@Command(mixinStandardHelpOptions = true)
class Day1Command : Callable<Int> {
    override fun call(): Int {
        runBlocking {
            val result = generateSequence(::readLine).asFlow()
                .day1()
            println(result)
        }
        return 0
    }
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
        "day1" to Day1Command(),
    )

    val cmd = CommandLine(MyHelpCommand())
    commands.forEach { cmd.addSubcommand(it.key, it.value) }
    val exitCode = cmd.execute(*args)
    exitProcess(exitCode)
}