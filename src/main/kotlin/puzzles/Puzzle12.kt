package puzzles

import java.io.File

private val lines = File("input/puzzle12/input.txt").readLines()

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
    fun isValidOption(option: String, validArrangements: List<Int>): Int {
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

        return if (option.contains('?') && validArrangements.take(damagedGroups.size) == damagedGroups
            || (!option.contains('?') && validArrangements == damagedGroups)
            || (option.contains('?') && damagedGroups.isNotEmpty() && consecutiveDamaged > 0
                    && damagedGroups.size <= validArrangements.size
                    && damagedGroups.last() < validArrangements[damagedGroups.size - 1])
        )
            damagedGroups.size
        else -1
    }

    val memo = mutableMapOf<Pair<String, List<Int>>, Long>()

    fun countCombinations(springsCombo: String, validArrangements: List<Int>, index: Int): Long {
        if (!springsCombo.contains('?')) {
            return 1
        }

        val s = springsCombo.fold("") { acc, c ->
            if (c == '.' && !acc.contains('?')) ""
            else acc + c
        }

        val key = Pair(s, validArrangements.subList(index, validArrangements.size))
        if (memo.containsKey(key)) {
            return memo[key]!!
        }

        var totalCount = 0L

        if (springsCombo.contains('?')) {
            val optionDot = springsCombo.replaceFirst('?', '.')
            var validOptionSize = isValidOption(optionDot, validArrangements)
            if (validOptionSize > -1) {
                totalCount += countCombinations(optionDot, validArrangements, validOptionSize)
            }
            val optionHash = springsCombo.replaceFirst('?', '#')
            validOptionSize = isValidOption(optionHash, validArrangements)
            if (validOptionSize > -1) {
                totalCount += countCombinations(optionHash, validArrangements, validOptionSize)
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

        countCombinations(unfoldedSprings, unfoldedArrangements, 0)
    }
}