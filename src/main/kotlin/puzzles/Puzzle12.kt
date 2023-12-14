package puzzles

import java.io.File

private val lines = File("input/puzzle12/testInput.txt").readLines()

fun puzzle12(): Long {
    fun getCombination(input: List<String>): List<String> {
        return if (input.none { it.contains("?") }) input
        else getCombination(input.flatMap {
            listOf(
                it.replaceFirst('?', '.'),
                it.replaceFirst('?', '#'),
            )
        })
    }

    return lines.sumOf {
        val (springs, arrangements) = it.split(" ")

        val springsCombo = getCombination(listOf(springs))

        springsCombo.sumOf { s ->
            val damagedGroups = mutableListOf<Int>()
            var consecutiveDamaged = 0

            s.forEach { c ->
                if (c == '#') consecutiveDamaged++
                else if (consecutiveDamaged > 0) {
                    damagedGroups.add(consecutiveDamaged)
                    consecutiveDamaged = 0
                }
            }
            // this accounts for the string ending in #
            if (consecutiveDamaged > 0) damagedGroups.add(consecutiveDamaged)

            if (damagedGroups == arrangements.split(",").map { it.toInt() }) 1L else 0L
        }
    }
}

fun puzzle12dot1(): Long {
    fun isValidOption(option: String, validArrangements: List<Int>): Boolean {
        val damagedGroups = mutableListOf<Int>()
        var consecutiveDamaged = 0
        for (c in option) {
            if (c == '?') break
            if (c == '#') consecutiveDamaged++
            else if (consecutiveDamaged > 0) {
                damagedGroups.add(consecutiveDamaged)
                consecutiveDamaged = 0
            }
        }
        if (consecutiveDamaged > 0) damagedGroups.add(consecutiveDamaged)

        return (option.contains('?') && validArrangements.take(damagedGroups.size) == damagedGroups)
                || (!option.contains('?') && validArrangements == damagedGroups)
                || (option.contains('?') && damagedGroups.isNotEmpty() && consecutiveDamaged > 0
                && damagedGroups.size <= validArrangements.size
                && validArrangements.take(damagedGroups.size - 1) == damagedGroups.dropLast(1)
                && damagedGroups.last() < validArrangements[damagedGroups.size - 1])
    }

    val memo = mutableMapOf<Pair<String, List<Int>>, Long>()

    fun countCombinations(springsCombo: List<String>, validArrangements: List<Int>): Long {
        if (springsCombo.all { !it.contains('?') }) {
            return springsCombo.size.toLong()
        }

        val key = Pair(springsCombo.toString(), validArrangements)
        if (memo.containsKey(key)) {
            return memo[key]!!
        }

        var totalCount = 0L

        for (i in springsCombo.indices) {
            if (springsCombo[i].contains('?')) {
                val optionDot = springsCombo.toMutableList()
                optionDot[i] = optionDot[i].replaceFirst('?', '.')
                if (isValidOption(optionDot[i], validArrangements)) {
                    totalCount += countCombinations(optionDot, validArrangements)
                }
                val optionHash = springsCombo.toMutableList()
                optionHash[i] = optionHash[i].replaceFirst('?', '#')
                if (isValidOption(optionHash[i], validArrangements)) {
                    totalCount += countCombinations(optionHash, validArrangements)
                }
            }
        }

        memo[key] = totalCount
        return totalCount
    }

    return lines.sumOf {
        val (springs, arrangements) = it.split(" ")
        val arrangementsList = arrangements.split(",").map { it.toInt() }

        var unfoldedSprings = springs
        val unfoldedArrangements = arrangementsList.toMutableList()

        for (i in 0 until 4) {
            unfoldedSprings += "?$springs"
            unfoldedArrangements.addAll(arrangementsList)
        }

        val springsCombo = listOf(unfoldedSprings)

        countCombinations(springsCombo, unfoldedArrangements)
    }
}