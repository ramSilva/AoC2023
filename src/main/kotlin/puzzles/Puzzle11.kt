package puzzles

import java.io.File

private val lines = File("input/puzzle11/input.txt").readLines()

data class Monke(
    var items: MutableList<Long> = mutableListOf(),
    var operation: (Long) -> Long = { 0L },
    var test: Int = 0,
    var testSuccess: Int = 0,
    var testFail: Int = 0,
    var handledItems: Long = 0
)

fun puzzle11(isPart1: Boolean): Long {
    val monkes = mutableListOf<Monke>()
    var currentMonke = -1

    lines.forEach {
        when {
            it.contains("Monkey") -> { // Monkey with capital M means it's the first line
                currentMonke = "\\w (\\d+):".toRegex().find(it)!!.groupValues[1].toInt()
                monkes.add(currentMonke, Monke())
            }
            it.contains("Starting items") -> {
                val startingItems =
                    "\\w+: (.*)".toRegex().find(it)!!.groupValues[1].split(", ")
                        .map { it.toLong() }
                monkes[currentMonke].items = startingItems.toMutableList()
            }
            it.contains("Operation") -> {
                val (operator, amount) = "\\w+ ([*|+]) (.*)".toRegex().find(it)!!.destructured
                val operation: (Long) -> Long =
                    if (operator == "+") {
                        if (amount == "old") {
                            { old -> old + old }
                        } else {
                            { old -> old + amount.toLong() }
                        }
                    } else {
                        if (amount == "old") {
                            { old -> old * old }
                        } else {
                            { old -> old * amount.toLong() }
                        }
                    }
                monkes[currentMonke].operation = operation
            }
            it.contains("Test") -> {
                val amount = ".* (\\d+)".toRegex().find(it)!!.groupValues[1].toInt()
                monkes[currentMonke].test = amount
            }
            it.contains("true") -> {
                val target = ".* (\\d+)".toRegex().find(it)!!.groupValues[1].toInt()
                monkes[currentMonke].testSuccess = target
            }
            it.contains("false") -> {
                val target = ".* (\\d+)".toRegex().find(it)!!.groupValues[1].toInt()
                monkes[currentMonke].testFail = target
            }
        }
    }

    val mcd = monkes.fold(1) { acc, monke -> acc * monke.test }

    for (i in 0 until if (isPart1) 20 else 10000) {
        monkes.forEach { monke ->
            val itemsCopy = monke.items.toMutableList()
            itemsCopy.forEach { item ->
                monke.handledItems++
                monke.items.removeFirst()
                var newWorry = monke.operation(item)
                if (isPart1) newWorry /= 3 else newWorry %= mcd

                if (newWorry % monke.test == 0L) {
                    monkes[monke.testSuccess].items.add(newWorry)
                } else {
                    monkes[monke.testFail].items.add(newWorry)
                }
            }
        }
    }

    return monkes.sortedByDescending { it.handledItems }
        .let { it[0].handledItems * it[1].handledItems }
}