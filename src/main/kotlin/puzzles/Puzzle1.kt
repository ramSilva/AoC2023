package puzzles

import java.io.File

private val lines = File("input/puzzle1/input.txt").readLines()
private val digits = mapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)
fun puzzle1() = lines.sumOf { (it.first{ c -> c.isDigit()}.toString() + it.last{c -> c.isDigit()}).toInt() }
fun puzzle1dot1() = lines.map { digits.entries.fold(it) { acc, (key, value) -> acc.replace(key, key.first() + value.toString() + key.substring(1)) } }.sumOf { (it.first{ c -> c.isDigit()}.toString() + it.last{c -> c.isDigit()}).toInt() }
