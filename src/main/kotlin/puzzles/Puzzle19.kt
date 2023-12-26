package puzzles

import java.io.File
import kotlin.math.abs

private val lines = File("input/puzzle19/input.txt").readLines()

fun puzzle19(): Int {
    val operationsString = lines.takeWhile { it.isNotEmpty() }
    val ratings = lines.takeLastWhile { it.isNotEmpty() }

    val operations = mutableMapOf<String, String>()
    operationsString.forEach {
        val (name, rest) = it.split("{")
        operations[name] = rest.dropLast(1)
    }

    val accepted = mutableMapOf<String, Int>(
        "x" to 0,
        "m" to 0,
        "a" to 0,
        "s" to 0,
    )

    val rejected = mutableMapOf<String, Int>(
        "x" to 0,
        "m" to 0,
        "a" to 0,
        "s" to 0,
    )

    val firstOp = "in"
    ratings.forEach {
        val parts = mutableMapOf<String, Int>()
        it.filter { it != '{' && it != '}' }.split(",").forEach {
            val (key, value) = it.split("=")
            parts[key] = value.toInt()
        }

        var currentOperation = operations[firstOp]!!
        outerLoop@ while (true) {
            innerLoop@ for (it in currentOperation.split(",")) {
                val (condition, target) = if (it.contains(':')) {
                    it.split(":")
                } else {
                    listOf("", it)
                }

                var matchesCondition = false

                if (condition.isNotEmpty()) {
                    val op = condition[1]
                    val partToCheck = condition.take(1)
                    val valueToCheck = condition.drop(2).toInt()

                    if (op == '<' && parts[partToCheck]!! < valueToCheck) {
                        matchesCondition = true
                    }
                    if (op == '>' && parts[partToCheck]!! > valueToCheck) {
                        matchesCondition = true
                    }

                } else {
                    matchesCondition = true
                }

                if (!matchesCondition) {
                    continue
                }

                when (target) {
                    "A" -> {
                        parts.forEach {
                            accepted[it.key] = accepted[it.key]!! + it.value
                        }
                        break@outerLoop
                    }

                    "R" -> {
                        parts.forEach {
                            rejected[it.key] = accepted[it.key]!! + it.value
                        }
                        break@outerLoop
                    }

                    else -> {
                        currentOperation = operations[target]!!
                        break@innerLoop
                    }
                }
            }
        }
    }

    return accepted.values.sum()
}

