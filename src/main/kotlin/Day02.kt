import java.io.File

enum class Figure(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    companion object {
        fun of(c: Char): Figure {
            return when (c) {
                'A', 'X' -> ROCK
                'B', 'Y' -> PAPER
                'C', 'Z' -> SCISSORS
                else -> throw RuntimeException("Invalid character")
            }
        }

        fun of(gameResult: GameResult, opponent: Figure): Figure {
            return when (gameResult) {
                GameResult.LOSS -> when (opponent) {
                    ROCK -> SCISSORS
                    PAPER -> ROCK
                    SCISSORS -> PAPER
                }

                GameResult.DRAW -> opponent

                GameResult.WIN -> when (opponent) {
                    ROCK -> PAPER
                    PAPER -> SCISSORS
                    SCISSORS -> ROCK
                }
            }
        }
    }
}

enum class GameResult(val score: Int) {
    LOSS(0),
    DRAW(3),
    WIN(6);

    companion object {
        fun of(c: Char): GameResult {
            return when (c) {
                'X' -> LOSS
                'Y' -> DRAW
                'Z' -> WIN
                else -> throw RuntimeException("Invalid character")
            }
        }

        fun of(opponent: Figure, me: Figure): GameResult {
            return when (opponent) {
                Figure.ROCK -> when (me) {
                    Figure.ROCK -> DRAW
                    Figure.PAPER -> WIN
                    Figure.SCISSORS -> LOSS
                }

                Figure.PAPER -> when (me) {
                    Figure.ROCK -> LOSS
                    Figure.PAPER -> DRAW
                    Figure.SCISSORS -> WIN
                }

                Figure.SCISSORS -> when (me) {
                    Figure.ROCK -> WIN
                    Figure.PAPER -> LOSS
                    Figure.SCISSORS -> DRAW
                }
            }
        }
    }
}

fun main() {
    val lines = File("inputs/inputDay02").readLines()
    val splits = lines.map { it.split(" ") }
    val games = splits.map { Pair(Figure.of(it[0][0]), Figure.of(it[1][0])) }
    val gamesWithStrategy = splits.map { Pair(Figure.of(it[0][0]), GameResult.of(it[1][0])) }
    val score1 = games.sumOf { calculateScore(it.first, it.second) }
    val score2 = gamesWithStrategy.sumOf { calculateScoreWithStrategy(it.first, it.second) }
    println("Puzzle answer to part 1 is $score1")
    println("Puzzle answer to part 2 is $score2")
}

private fun calculateScore(opponent: Figure, me: Figure): Int {
    return GameResult.of(opponent, me).score + me.score
}

private fun calculateScoreWithStrategy(opponent: Figure, gameResult: GameResult): Int {
    return gameResult.score + Figure.of(gameResult, opponent).score
}
