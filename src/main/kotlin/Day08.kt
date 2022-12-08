import java.io.File

fun main() {
    val lines = File("inputs/inputDay08").readLines()
    val trees = trees(lines)
    val treesVisibleHorizontally = trees.map { horizontalTrees -> isVisible(horizontalTrees) }
    val treesVisibleVerticallyFlipped = trees.indices.map { y -> trees.map { row -> row[y] } }
        .map { verticalTrees -> isVisible(verticalTrees) }

    val treesVisibleVertically = treesVisibleVerticallyFlipped.indices.map { y ->
        treesVisibleVerticallyFlipped[y].indices.map { x -> treesVisibleVerticallyFlipped[x][y] }
    }

    val visibleTrees = visibleTrees(trees, treesVisibleHorizontally, treesVisibleVertically)

    val scenicScoreInHorizontal = trees.map { horizontalTrees -> scenicScore(horizontalTrees) }
    val scenicScoreInVerticalFlipped = trees.indices.map { y -> trees.map { row -> row[y] } }
        .map { verticalTrees -> scenicScore(verticalTrees) }

    val scenicScoreInVertical = scenicScoreInVerticalFlipped.indices.map { y ->
        scenicScoreInVerticalFlipped[y].indices
            .map { x -> scenicScoreInVerticalFlipped[x][y] }
    }

    val scenicScores = scenicScores(trees, scenicScoreInHorizontal, scenicScoreInVertical)

    println("Puzzle answer to part 1 is ${visibleTrees.flatten().count { isVisible -> isVisible }}")
    println("Puzzle answer to part 2 is ${scenicScores.flatten().max()}")
}

private fun isVisible(trees: List<Int>): List<Boolean> {
    return trees.mapIndexed { index, tree ->
        val treesBefore = trees.slice(0 until index)
        val treesAfter = trees.slice(index + 1 until trees.size)
        treesBefore.none { it >= tree } || treesAfter.none { it >= tree }
    }
}

private fun scenicScores(
    trees: List<List<Int>>, scenicScoresInHorizontal: List<List<Int>>,
    scenicScoresInVertical: List<List<Int>>
): List<List<Int>> {
    return trees.indices.map { y ->
        trees[y].indices.map { x ->
            if (x == 0 || x == trees[y].size - 1
                || y == 0 || y == trees[y].size - 1
            ) {
                0
            } else {
                scenicScoresInHorizontal[y][x] * scenicScoresInVertical[y][x]
            }
        }
    }
}

private fun visibleTrees(
    trees: List<List<Int>>, treesVisibleHorizontally: List<List<Boolean>>,
    treesVisibleVertically: List<List<Boolean>>
): List<List<Boolean>> {
    return trees.indices.map { y ->
        trees[y].indices.map { x ->
            treesVisibleHorizontally[y][x]
                    || treesVisibleVertically[y][x]
        }
    }.mapIndexed { y, row ->
        row.mapIndexed { x, isVisible ->
            isVisible
                    || x == 0 || x == row.size - 1
                    || y == 0 || y == row.size - 1
        }
    }
}

private fun scenicScore(trees: List<Int>): List<Int> {
    return trees.mapIndexed { index, tree ->
        val treesBefore = trees.slice(0 until index)
        val treesAfter = trees.slice(index + 1 until trees.size)

        var scenicScoreBefore = 0
        for (treeBefore in treesBefore.reversed()) {
            scenicScoreBefore++
            if (treeBefore >= tree) {
                break
            }
        }

        var scenicScoreAfter = 0
        for (treeAfter in treesAfter) {
            scenicScoreAfter++
            if (treeAfter >= tree) {
                break
            }
        }

        scenicScoreBefore * scenicScoreAfter
    }
}

private fun trees(lines: List<String>): List<List<Int>> {
    return lines.map { line ->
        line.map { c ->
            c.digitToInt()
        }
    }
}
