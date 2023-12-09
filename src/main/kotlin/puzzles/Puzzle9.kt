package puzzles

import java.io.File
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sqrt

private val lines = File("input/puzzle9/input.txt").readLines()

fun puzzle9(): Long {
    var sumOfNext = 0L
    lines.forEach {
        var numbers = it.split(" ").map { it.toLong() }
        var next = numbers.last()

        while(true){
            if (numbers.all { it == 0L }) break

            val differences = mutableListOf<Long>()
            numbers.zipWithNext { a, b -> differences.add(b - a) }
            next += differences.last()

            numbers = differences
        }

        sumOfNext += next
    }

    return sumOfNext
}

fun puzzle9dot1(): Long {
    var sumOfNext = 0L
    lines.forEach {
        var numbers = it.split(" ").map { it.toLong() }.reversed()
        var next = numbers.last()

        while(true){
            if (numbers.all { it == 0L }) break

            val differences = mutableListOf<Long>()
            numbers.zipWithNext { a, b -> differences.add(b - a) }
            next += differences.last()

            numbers = differences
        }

        sumOfNext += next
    }

    return sumOfNext
}