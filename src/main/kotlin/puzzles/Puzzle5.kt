package puzzles

import java.io.File

private val lines = File("input/puzzle5/input.txt").readLines()

fun puzzle5(): String {
    val stacks = List<ArrayDeque<Char>>(9) { ArrayDeque() }
    val inputStacks: List<String>
    val operations: List<String>
    lines.groupBy {
        it.contains("move")
    }.also {
        inputStacks = it[false]!!
        operations = it[true]!!
    }

    inputStacks.forEach {
        it.asSequence().forEachIndexed { i, c ->
            if (c.isLetter()) {
                stacks[(i - 1) / 4].addLast(c)
            }
        }
    }


    operations.forEach {
        val regex = Regex("move (\\d+) from (\\d+) to (\\d+)")
        val (amount, from, to) = regex.find(it)!!.destructured
        for (i in 0 until amount.toInt()) {
            stacks[to.toInt() - 1].addFirst(stacks[from.toInt() - 1].removeFirst())
        }
    }

    return stacks.fold("") { acc, chars -> acc + chars.first() }
}

fun puzzle5dot1(): String {
    val stacks = List<ArrayDeque<Char>>(9) { ArrayDeque() }
    val inputStacks: List<String>
    val operations: List<String>
    lines.groupBy {
        it.contains("move")
    }.also {
        inputStacks = it[false]!!
        operations = it[true]!!
    }

    inputStacks.forEach {
        it.asSequence().forEachIndexed { i, c ->
            if (c.isLetter()) {
                stacks[(i - 1) / 4].addLast(c)
            }
        }
    }


    operations.forEach {
        val regex = Regex("move (\\d+) from (\\d+) to (\\d+)")
        val (amount, from, to) = regex.find(it)!!.destructured
        var tempBoxes = ""
        for (i in 0 until amount.toInt()) {
            tempBoxes = stacks[from.toInt() - 1].removeFirst() + tempBoxes
        }

        tempBoxes.forEach {
            stacks[to.toInt() - 1].addFirst(it)
        }
    }

    return stacks.fold("") { acc, chars -> acc + chars.first() }
}