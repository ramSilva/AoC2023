package puzzles

import java.io.File
import kotlin.math.pow

private val lines = File("input/puzzle25/input.txt").readLines()

fun puzzle25(): String {
    var base10 = lines.sumOf {
        it.reversed().foldIndexed(0L) { index, acc, c ->
            acc + (if (c.isDigit()) c.digitToInt() else if (c == '-') -1 else -2) * 5.0.pow(index)
                .toLong()
        }.toLong()
    }

    println(base10)

    var base5 = ""
    var acum: Long
    while (true) {
        when (val remainder = base10 % 5) {
            3L -> {
                acum = 1
                base5 = "=$base5"
            }
            4L -> {
                acum = 1
                base5 = "-$base5"
            }
            else -> {
                acum = 0
                base5 = "$remainder$base5"
            }
        }

        base10 = (base10 / 5) + acum
        if (base10 == 0L) break
    }

    return base5
}