package puzzles

import java.io.File

private val lines = File("input/puzzle8/input.txt").readLines()

fun puzzle8(): Int {
    val map = mutableMapOf<String, Pair<String, String>>()

    val pattern = lines[0]

    lines.subList(2, lines.size).forEach {
        val (name, left, right) = "[A-Z]+".toRegex().findAll(it).map { it.value }.toList()

        map[name] = Pair(left, right)
    }

    var currentNode = "AAA"
    var operationIndex = 0

    var steps = 0
    while (true) {
        if (currentNode == "ZZZ") break

        currentNode =
            if (pattern[operationIndex] == 'L') map[currentNode]!!.first else map[currentNode]!!.second

        operationIndex = (operationIndex + 1) % pattern.toCharArray().size
        steps++
    }

    return steps
}

fun findGCD(a: Long, b: Long): Long {
    return if (b == 0L) a else findGCD(b, a % b)
}

fun findLCM(a: Long, b: Long): Long {
    return if (a == 0L || b == 0L) 0 else (a * b) / findGCD(a, b)
}

fun puzzle8dot1(): Long {
    val map = mutableMapOf<String, Pair<String, String>>()

    val pattern = lines[0]

    lines.subList(2, lines.size).forEach {
        val (name, left, right) = "[A-Z|1-9]+".toRegex().findAll(it).map { it.value }.toList()

        map[name] = Pair(left, right)
    }

    var currentNodes = map.filter { it.key.last() == 'A' }.map { it.key }.toList()
    val timeToEnd = MutableList(currentNodes.size) { -1L }

    var operationIndex = 0
    var steps = 0

    while (true) {
        val nextNodes = mutableSetOf<String>()

        if (timeToEnd.all { it > -1 }) break

        currentNodes.forEachIndexed { i, it ->
            val nextNode = if (pattern[operationIndex] == 'L') map[it]!!.first else map[it]!!.second
            nextNodes.add(nextNode)

            if (it.last() == 'Z' && timeToEnd[i] == -1L) timeToEnd[i] = steps.toLong()
        }

        currentNodes = nextNodes.toList()
        operationIndex = (operationIndex + 1) % pattern.toCharArray().size
        steps++
    }

    return timeToEnd.reduce { acc, l -> findLCM(acc, l) }
}