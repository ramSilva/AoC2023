package puzzles

import java.io.File

private val lines = File("input/puzzle4/input.txt").readLines()

fun puzzle4() = lines.count {
    it.split(',')
        .sortedBy { it.split('-')[0].toInt() }
        .sortedByDescending { it.split('-')[1].toInt() }
        .let {
            it[0].split('-')[0].toInt() <= it[1].split('-')[0].toInt()
                && it[0].split('-')[1].toInt() >= it[1].split('-')[1].toInt()
        }
}

fun puzzle4alt() = lines
    .map {
        it.split(',').map { (it.split('-')[0].toInt()..it.split('-')[1].toInt()).toSet() }
    } //turn 1-4 into [1, 2, 3, 4]
    .count {
        it[0].intersect(it[1])
            .let { intersection -> intersection.size == it[0].size || intersection.size == it[1].size }
    }

fun puzzle4dot1() = lines
    .map {
        it.split(',').map { (it.split('-')[0].toInt()..it.split('-')[1].toInt()).toSet() }
    } //turn 1-4 into [1, 2, 3, 4]
    .count { it[0].intersect(it[1]).isNotEmpty() }
