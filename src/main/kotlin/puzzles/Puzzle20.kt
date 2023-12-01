package puzzles

import java.io.File

private val lines = File("input/puzzle20/input.txt").readLines()

data class Entry(
    val value: Long,
    var moveIndex: Int
)

fun puzzle20(): Long {
    val encrypted = lines.mapIndexed { index, s -> Entry(s.toLong() * 811589153, index) }.toMutableList()

    for(n in 0 until 10){
        for (i in 0 until encrypted.size) {
            val toMove = encrypted.find { it.moveIndex == i }
            val toMoveIndex = encrypted.indexOf(toMove)
            var targetIndex = ((toMove!!.value + toMoveIndex) % (encrypted.size - 1)).toInt()
            if (targetIndex < 0) targetIndex += encrypted.size - 1
            if (targetIndex == 0) targetIndex = encrypted.size - 1

            for (j in toMoveIndex until targetIndex) {
                if (j + 1 >= encrypted.size) break
                encrypted[j] = encrypted[j + 1]
            }
            for (j in toMoveIndex downTo targetIndex + 1) {
                encrypted[j] = encrypted[j - 1]
            }

            encrypted[targetIndex] = toMove
        }
    }

    val zero = encrypted.find { it.value == 0L }

    return encrypted[(1000 + encrypted.indexOf(zero)) % (encrypted.size)].value + encrypted[(2000 + encrypted.indexOf(
        zero
    )) % (encrypted.size)].value + encrypted[(3000 + encrypted.indexOf(zero)) % (encrypted.size)].value
}
