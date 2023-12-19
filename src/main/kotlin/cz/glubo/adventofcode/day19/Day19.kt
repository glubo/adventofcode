package cz.glubo.adventofcode.day19

import cz.glubo.adventofcode.day19.Opcode.APPROVE
import cz.glubo.adventofcode.day19.Opcode.CMP
import cz.glubo.adventofcode.day19.Opcode.ICMP
import cz.glubo.adventofcode.day19.Opcode.JUMP
import cz.glubo.adventofcode.day19.Opcode.REJECT
import cz.glubo.adventofcode.utils.getGroup
import cz.glubo.adventofcode.utils.length
import cz.glubo.adventofcode.utils.split
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

private val logger = noCoLogger({}.javaClass.`package`.toString())

enum class Opcode {
    CMP,
    ICMP,
    APPROVE,
    REJECT,
    JUMP,
}

data class Rule(
    val opcode: Opcode,
    val attribute: Attribute,
    val param: Int,
    val target: String,
)

data class Workflow(
    val name: String,
    val rules: List<Rule>,
)

@Suppress("ktlint:standard:enum-entry-name-case")
enum class Attribute {
    x,
    m,
    a,
    s,
}

data class Part(
    val attributes: Map<Attribute, Int>,
) {
    fun value() = attributes.values.sum().toLong()
}

// a<2006:qkq
val comparisonOpRegex = """^(?<attr>[xmas])(?<sign>[<>])(?<param>\d+):(?<target>\w+)$""".toRegex()
val jumopOpRegex = """^\w+$""".toRegex()

fun parseComparisonOp(input: String): Rule {
    val match = comparisonOpRegex.matchEntire(input)!!
    return Rule(
        opcode = if (match.getGroup("sign") == ">") CMP else ICMP,
        param = match.getGroup("param").toInt(),
        target = match.getGroup("target"),
        attribute = Attribute.valueOf(match.getGroup("attr")),
    )
}

suspend fun Flow<String>.day19part1(): Long {
    val lines = this.toList()
    val workflows = parseWorkflows(lines)

    val parts =
        lines.dropWhile { it != "" }
            .drop(1)
            .map { partLine ->
                // {x=787,m=2655,a=1222,s=2876}
                val attributes: Map<Attribute, Int> =
                    partLine.removePrefix("{")
                        .removeSuffix("}")
                        .split(",")
                        .associate { attributeString: String ->
                            val (attribute, value) = attributeString.split("=")
                            val attributeValue = Attribute.valueOf(attribute)
                            attributeValue to value.toInt()
                        }
                Part(attributes)
            }
    logger.debug { "Parts: $parts" }

    val acceptedParts = parts.filter { applyRules(workflows, it) }

    logger.debug { "Accepted Parts: $acceptedParts" }

    return acceptedParts.sumOf { it.value() }
}

private fun parseWorkflows(lines: List<String>) =
    lines.takeWhile { it != "" }
        .map { line ->
            // px{a<2006:qkq,m>2090:A,rfg}
            val name = line.takeWhile { it != '{' }
            val rules =
                line.removeSuffix("}")
                    .removePrefix("$name{")
                    .split(",")
                    .map { opString ->
                        when {
                            comparisonOpRegex.matches(opString) -> parseComparisonOp(opString)
                            opString == "R" -> Rule(REJECT, Attribute.a, 0, "")
                            opString == "A" -> Rule(APPROVE, Attribute.a, 0, "")
                            jumopOpRegex.matches(opString) -> Rule(JUMP, Attribute.a, 0, opString)
                            else -> throw RuntimeException("unknown op $opString")
                        }
                    }
            Workflow(
                name = name,
                rules = rules,
            )
        }

fun opCompare(
    i: Int,
    rule: Rule,
) = if (rule.opcode == ICMP) {
    i < rule.param
} else {
    i > rule.param
}

fun applyRules(
    workflows: List<Workflow>,
    part: Part,
): Boolean {
    val ruleMap = workflows.associateBy { it.name }
    var currentRule = ruleMap["in"]!!
    while (true) {
        for (op in currentRule.rules) {
            when (op.opcode) {
                CMP, ICMP ->
                    if (opCompare(part.attributes[op.attribute]!!, op)) {
                        if (op.target in "RA") return op.target == "A"
                        currentRule = ruleMap[op.target]!!
                        break
                    }

                APPROVE -> return true
                REJECT -> return false
                JUMP -> currentRule = ruleMap[op.target]!!
            }
        }
    }
}

suspend fun Flow<String>.day19part2(): Long {
    val lines = this.toList()
    val workflows =
        parseWorkflows(lines) +
            listOf(
                Workflow("A", listOf(Rule(APPROVE, Attribute.x, 0, ""))),
                Workflow("R", listOf(Rule(REJECT, Attribute.x, 0, ""))),
            )

    var sum = 0L

    val workflowMap = workflows.associateBy { it.name }

    val toExplore =
        mutableListOf(
            workflowMap["in"]!! to
                mapOf(
                    Attribute.x to (1..4000),
                    Attribute.m to (1..4000),
                    Attribute.a to (1..4000),
                    Attribute.s to (1..4000),
                ),
        )

    while (toExplore.isNotEmpty()) {
        val (currentWorkflow, currentRanges) = toExplore.removeLast()

        val remainingRanges = currentRanges.toMutableMap()
        currentWorkflow.rules.forEach {
            logger.debug { "$it $remainingRanges" }
            when (it.opcode) {
                CMP -> {
                    val split = remainingRanges[it.attribute]!!.split(it.param + 1)
                    split.second?.let { higher ->
                        toExplore.add(
                            workflowMap[it.target]!! to
                                remainingRanges.toMutableMap()
                                    .let { map ->
                                        map[it.attribute] = higher
                                        map
                                    },
                        )
                    }
                    split.first?.let { lower ->
                        remainingRanges[it.attribute] = lower
                    }
                }

                ICMP -> {
                    val split = remainingRanges[it.attribute]!!.split(it.param)
                    split.first?.let { lower ->
                        toExplore.add(
                            workflowMap[it.target]!! to
                                remainingRanges.toMutableMap()
                                    .let { map ->
                                        map[it.attribute] = lower
                                        map
                                    },
                        )
                    }
                    split.second?.let { higher ->
                        remainingRanges[it.attribute] = higher
                    }
                }

                APPROVE ->
                    sum +=
                        remainingRanges.values.fold(1L) { acc, i ->
                            acc * i.length()
                        }
                REJECT -> {}
                JUMP ->
                    toExplore.add(
                        workflowMap[it.target]!! to remainingRanges,
                    )
            }
        }
    }

    return sum
}
