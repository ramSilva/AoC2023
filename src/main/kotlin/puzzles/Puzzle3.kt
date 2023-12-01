package puzzles

import java.io.File

private val lines = File("input/puzzle3/input.txt").readLines()

fun puzzle3() = lines.sumOf { it.chunked(it.length/2).let { it[0].toSet().intersect(it[1].toSet()) }.first().let { if(it.isUpperCase()) it - 'A' + 27 else it - 'a' + 1 } }

fun puzzle3dot1() = lines.chunked(3).sumOf{ it[0].toSet().intersect(it[1].toSet()).intersect(it[2].toSet()).first().let { if(it.isUpperCase()) it - 'A' + 27 else it - 'a' + 1 } }
