package puzzles

import java.io.File
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sqrt

private val lines = File("input/puzzle9/input.txt").readLines()

fun puzzle9() = lines.sumOf {
    var numbers = it.split(" ").map { it.toLong() }
    var nextInSequence = numbers.last()

    while (numbers.any { it != 0L }) {
        numbers = numbers.zipWithNext { a, b -> b - a }
        nextInSequence += numbers.last()
    }

    nextInSequence
}

fun puzzle9dot1() = lines.sumOf {
    var numbers = it.split(" ").map { it.toLong() }.reversed()
    var nextInSequence = numbers.last()

    while (numbers.any { it != 0L }) {
        numbers = numbers.zipWithNext { a, b -> b - a }
        nextInSequence += numbers.last()
    }

    nextInSequence
}