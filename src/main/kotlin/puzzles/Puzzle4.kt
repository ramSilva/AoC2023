package puzzles

import java.io.File
import kotlin.math.floor
import kotlin.math.pow

private val lines = File("input/puzzle4/input.txt").readLines()

fun puzzle4() = lines.sumOf {
    val winningNumbers =
        "\\d+".toRegex().findAll(it.split(": ")[1].split(" | ")[0]).map { it.value.toInt() }
            .toList()
    val myNumbers =
        "\\d+".toRegex().findAll(it.split(": ")[1].split(" | ")[1]).map { it.value.toInt() }
            .toList()

    floor(2.0.pow(winningNumbers.count { myNumbers.contains(it) } - 1))
}

fun puzzle4dot1(): Int {
    // `cards` represents how many of each card we have. We begin with 1 of each card
    val cards = MutableList(lines.size) { 1 }
    lines.forEachIndexed { index, s ->
        val winningNumbers =
            "\\d+".toRegex().findAll(s.split(": ")[1].split(" | ")[0]).map { it.value.toInt() }
                .toList()
        val myNumbers =
            "\\d+".toRegex().findAll(s.split(": ")[1].split(" | ")[1]).map { it.value.toInt() }
                .toList()

        // if we're processing card 3 and we have 4 matches, then we need to add cards[4..7]
        // we had however many of card 3 we have (because each copy adds one more)
        for (i in index + 1..index + winningNumbers.count { myNumbers.contains(it) }) {
            cards[i] = cards[i] + cards[index]
        }
    }
    return cards.sum()
}
