package puzzles

import java.io.File

private val lines = File("input/puzzle8/input.txt").readLines()

fun puzzle8(): Int {
    val gridSize = lines[0].length
    val seenTrees = mutableSetOf<Pair<Int, Int>>()

    // left to right
    for (i in 0 until gridSize) {
        var highestTree = -1
        for (j in 0 until gridSize) {
            val tree = lines[i][j].digitToInt()
            if (tree > highestTree) {
                seenTrees.add(Pair(i, j))
                highestTree = tree
            }
        }
    }
    // right to left
    for (i in 0 until gridSize) {
        var highestTree = -1
        for (j in (gridSize - 1) downTo 0) {
            val tree = lines[i][j].digitToInt()
            if (tree > highestTree) {
                seenTrees.add(Pair(i, j))
                highestTree = tree
            }
        }
    }
    // top to bottom
    for (i in 0 until gridSize) {
        var highestTree = -1
        for (j in 0 until gridSize) {
            val tree = lines[j][i].digitToInt()
            if (tree > highestTree) {
                highestTree = tree
                seenTrees.add(Pair(j, i))
            }
        }
    }
    // bottom to top
    for (i in 0 until gridSize) {
        var highestTree = -1
        for (j in (gridSize - 1) downTo 0) {
            val tree = lines[j][i].digitToInt()
            if (tree > highestTree) {
                highestTree = tree
                seenTrees.add(Pair(j, i))
            }
        }
    }

    return seenTrees.size
}

fun puzzle8alt(): Int {
    val seenTrees = mutableSetOf<Pair<Int, Int>>()
    val gridSize = lines[0].length

    lines.forEachIndexed { i, s ->
        s.forEachIndexed { j, c ->
            val tree = c.digitToInt()
            var coverDirections = 0

            // check left
            for (x in j - 1 downTo 0) {
                if (tree <= lines[i][x].digitToInt()) {
                    coverDirections++
                    break
                }
            }

            // check right
            for (x in j + 1 until gridSize) {
                if (tree <= lines[i][x].digitToInt()) {
                    coverDirections++
                    break
                }
            }

            // check top
            for (y in i - 1 downTo 0) {
                if (tree <= lines[y][j].digitToInt()) {
                    coverDirections++
                    break
                }
            }

            // check bottom
            for (y in i + 1 until gridSize) {
                if (tree <= lines[y][j].digitToInt()) {
                    coverDirections++
                    break
                }
            }

            if (coverDirections < 4) seenTrees.add(Pair(j, i))
        }
    }

    return seenTrees.size
}

fun puzzle8dot1(): Int? {
    val scores = mutableListOf<Int>()
    val gridSize = lines[0].length

    lines.forEachIndexed { i, s ->
        s.forEachIndexed { j, c ->
            val tree = c.digitToInt()

            // check left
            var leftCount = 0
            for (x in j - 1 downTo 0) {
                leftCount++
                if (tree <= lines[i][x].digitToInt()) {
                    break
                }
            }

            // check right
            var rightCount = 0
            for (x in j + 1 until gridSize) {
                rightCount++
                if (tree <= lines[i][x].digitToInt()) {
                    break
                }
            }

            // check top
            var topCount = 0
            for (y in i - 1 downTo 0) {
                topCount++
                if (tree <= lines[y][j].digitToInt()) {
                    break
                }
            }

            // check bottom
            var bottomCount = 0
            for (y in i + 1 until gridSize) {
                bottomCount++
                if (tree <= lines[y][j].digitToInt()) {
                    break
                }
            }

            scores.add(leftCount * rightCount * topCount * bottomCount)
        }
    }

    return scores.maxOrNull()
}