package puzzles

import java.io.File

private val lines = File("input/puzzle7/input.txt").readLines()

private data class Hand(
    val cards: String,
    val bid: Int
)

fun puzzle7(): Int {
    val hands = mutableMapOf<String, MutableList<Hand>>(
        "highCard" to mutableListOf(),
        "onePair" to mutableListOf(),
        "twoPair" to mutableListOf(),
        "threeOfAKind" to mutableListOf(),
        "fullHouse" to mutableListOf(),
        "fourOfAKind" to mutableListOf(),
        "poker" to mutableListOf(),
    )

    // This will order hands from the least valuable to the most valuable. It helps when calculating winnings
    val ordering = mapOf(
        'A' to "14",
        'K' to "13",
        'Q' to "12",
        'J' to "11",
        'T' to "10",
        '9' to "09",
        '8' to "08",
        '7' to "07",
        '6' to "06",
        '5' to "05",
        '4' to "04",
        '3' to "03",
        '2' to "02",
        '1' to "01"
    )

    lines.forEach {
        val (cards, bid) = it.split(" ")

        // I hate kotlin sometimes. How is this the best way to sort a string
        val orderedCards =
            cards.toCharArray().sortedBy { ordering[it] }.fold("") { acc, c -> acc + c }

        if (orderedCards.filter { it == orderedCards[2] }.length == 5) {
            hands["poker"]!!.add(Hand(cards, bid.toInt()))
            return@forEach
        }

        if (orderedCards.filter { it == orderedCards[2] }.length == 4) {
            hands["fourOfAKind"]!!.add(Hand(cards, bid.toInt()))
            return@forEach
        }

        if (
            orderedCards.filter { it == orderedCards[0] }.length == 3 && orderedCards.filter { it == orderedCards[4] }.length == 2 ||
            orderedCards.filter { it == orderedCards[0] }.length == 2 && orderedCards.filter { it == orderedCards[4] }.length == 3
        ) {
            hands["fullHouse"]!!.add(Hand(cards, bid.toInt()))
            return@forEach
        }

        if (orderedCards.filter { it == orderedCards[2] }.length == 3) {
            hands["threeOfAKind"]!!.add(Hand(cards, bid.toInt()))
            return@forEach
        }

        if (orderedCards.toCharArray().distinct().size == 3) {
            hands["twoPair"]!!.add(Hand(cards, bid.toInt()))
            return@forEach
        }

        if (orderedCards.toCharArray().distinct().size == 4) {
            hands["onePair"]!!.add(Hand(cards, bid.toInt()))
            return@forEach
        } else {
            hands["highCard"]!!.add(Hand(cards, bid.toInt()))
        }
    }

    // Hidethepainharold.png
    val orderedHands = hands.toMutableMap()
    hands.forEach {
        orderedHands[it.key] = it.value.sortedWith(compareBy {
            it.cards.toCharArray()
                .map { ordering[it]!! }
                .fold("") { acc, i -> acc + i }
        }).toMutableList()
    }

    var winnings = 0
    var i = 1

    orderedHands.forEach {
        it.value.forEach {
            winnings += it.bid * i
            i++
        }
    }

    return winnings
}

fun puzzle7dot1(): Int {
    val hands = mutableMapOf<String, MutableList<Hand>>(
        "highCard" to mutableListOf(),
        "onePair" to mutableListOf(),
        "twoPair" to mutableListOf(),
        "threeOfAKind" to mutableListOf(),
        "fullHouse" to mutableListOf(),
        "fourOfAKind" to mutableListOf(),
        "poker" to mutableListOf(),
    )

    // This will order hands from the least valuable to the most valuable. It helps when calculating winnings
    val ordering = mapOf(
        'A' to "14",
        'K' to "13",
        'Q' to "12",
        'T' to "11",
        '9' to "10",
        '8' to "09",
        '7' to "08",
        '6' to "07",
        '5' to "06",
        '4' to "05",
        '3' to "04",
        '2' to "03",
        '1' to "02",
        'J' to "01",
    )

    val handsOrdering = mapOf(
        "poker" to 7,
        "fourOfAKind" to 6,
        "fullHouse" to 5,
        "threeOfAKind" to 4,
        "twoPair" to 3,
        "onePair" to 2,
        "highCard" to 1,
    )

    lines.forEach {
        val (cards, bid) = it.split(" ")

        val pretendHands = mutableSetOf<String>()
        ordering.forEach {
            if(it.key != 'J') {
                pretendHands.add(cards.replace('J', it.key))
            }
        }

        var bestHand = -1
        pretendHands.forEach {
            // I hate kotlin sometimes. How is this the best way to sort a string
            val orderedCards =
                it.toCharArray().sortedBy { ordering[it] }.fold("") { acc, c -> acc + c }

            if (orderedCards.filter { it == orderedCards[2] }.length == 5) {
                bestHand = maxOf(handsOrdering["poker"]!!, bestHand)
                return@forEach
            }

            if (orderedCards.filter { it == orderedCards[2] }.length == 4) {
                bestHand = maxOf(handsOrdering["fourOfAKind"]!!, bestHand)
                return@forEach
            }

            if (
                orderedCards.filter { it == orderedCards[0] }.length == 3 && orderedCards.filter { it == orderedCards[4] }.length == 2 ||
                orderedCards.filter { it == orderedCards[0] }.length == 2 && orderedCards.filter { it == orderedCards[4] }.length == 3
            ) {
                bestHand = maxOf(handsOrdering["fullHouse"]!!, bestHand)
                return@forEach
            }

            if (orderedCards.filter { it == orderedCards[2] }.length == 3) {
                bestHand = maxOf(handsOrdering["threeOfAKind"]!!, bestHand)
                return@forEach
            }

            if (orderedCards.toCharArray().distinct().size == 3) {
                bestHand = maxOf(handsOrdering["twoPair"]!!, bestHand)
                return@forEach
            }

            if (orderedCards.toCharArray().distinct().size == 4) {
                bestHand = maxOf(handsOrdering["onePair"]!!, bestHand)
                return@forEach
            } else {
                bestHand = maxOf(handsOrdering["highCard"]!!, bestHand)
            }
        }

        hands[handsOrdering.filter { it.value == bestHand }.keys.first()]!!.add(Hand(cards, bid.toInt()))
    }

    // Hidethepainharold.png
    val orderedHands = hands.toMutableMap()
    hands.forEach {
        orderedHands[it.key] = it.value.sortedWith(compareBy {
            it.cards.toCharArray()
                .map { ordering[it]!! }
                .fold("") { acc, i -> acc + i }
        }).toMutableList()
    }

    var winnings = 0
    var i = 1

    orderedHands.forEach {
        it.value.forEach {
            winnings += it.bid * i
            i++
        }
    }

    return winnings
}