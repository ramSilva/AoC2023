package puzzles

import java.io.File

private val lines = File("input/puzzle2/input.txt").readLines()

/**
 * To get the score from my move: Subtract my move from X (to reduce it to 0) and add 1 (given rock is 1 point, not 0 points)
 *
 * To get the point from the matchup (win/lose/draw): normalize my play against the opponent (make it so X matches A: X-23=A).
 * Calculate myMove - opponentMove and add 1 so that tie=1, win=2 or -1, lose=3 or 0.
     * e.g.: A-A=0+1=1, tie. A-B=-1+1=0, lose. A-Z(C)=-2+1=-1, win
 * Add 3 and mod3. This magically makes every result the correct one, including negative values.
    * A loss (0 or 3) turns to 0 (3+3=6mod3=0). A tie (1) turns to 1 (1+3=4mod3=1). A win (1 or -2) turns to 2 (2+3=5mod3=2. -1+3=2mod3=2)
 * Finally, multiply the result by 3 to get the possible scores of 0, 3 and 6
 */
fun puzzle2() = lines.sumOf { (it[2] - it[0] - 23 + 1 + 3) % 3 * 3 + (it[2] - 'X' + 1) }

/**
 * To the the score from the matchup (win/lose/draw): subtract the result from 'X' (reduce to 0) and multiply by 3
 *
 * To find out what my move should be: subtract the result from 'Y'. This means lose (X) = X-Y=-1, tie (Y) = Y-Y=0, win (Z) = Z-Y=1
 * Then, apply that result to the opponent's move. Tie keeps it the same, win means "play the next letter", lose means "play the previous letter"
 * Then apply the previous operation of adding 3 mod3 to make negative values positive while keeping positive values the same
 */
fun puzzle2dot1() = lines.sumOf { (((it[0] - 'A') + (it[2] - 'Y')) + 3) % 3 + 1 + (it[2] - 'X') * 3 }
