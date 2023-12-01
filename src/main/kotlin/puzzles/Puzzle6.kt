package puzzles

import java.io.File

private val lines = File("input/puzzle6/input.txt").readLines()

fun puzzle6(): String = run run@{ lines[0].foldIndexed("") {i, acc, c -> if(acc.toSet().size == 4) return@run "$i" else acc.takeLast(3) + c}}

fun puzzle6dot1(): String = run run@{ lines[0].foldIndexed("") {i, acc, c -> if(acc.toSet().size == 14) return@run "$i" else acc.takeLast(13) + c}}
