import java.io.File
import java.util.*

private data class Move(val quantity: Int, val origin: Int, val destination: Int) {
    companion object {
        fun from(line: String): Move {
            val quantity = line.split(" ")[1].toInt()
            val origin = line.split(" ")[3].toInt()
            val destination = line.split(" ")[5].toInt()
            return Move(quantity, origin, destination)
        }
    }
}

fun main() {
    val lines = File("inputs/inputDay05").readLines()
    val firstInstructionLineIndex = lines.indexOfFirst { it.startsWith("move") }
    val instructionLines = lines.subList(firstInstructionLineIndex, lines.size)
    val moves = instructionLines.map(Move::from)
    val movedCrates = moveCratesOneByOne(createStacks(lines), moves)
    val topCrates = createTopCratesString(movedCrates)
    val movedCratesWithNewCrane = moveCratesInBatches(createStacks(lines), moves)
    val topCratesWithNewCrane = createTopCratesString(movedCratesWithNewCrane)

    println("Puzzle answer to part 1 is $topCrates")
    println("Puzzle answer to part 2 is $topCratesWithNewCrane")
}

private fun moveCratesOneByOne(crateStacks: List<Stack<Char>>, moves: List<Move>): List<Stack<Char>> {
    for (move in moves) {
        repeat(move.quantity) {
            crateStacks[move.destination - 1].push(crateStacks[move.origin - 1].pop())
        }
    }
    return crateStacks
}

private fun moveCratesInBatches(crateStacks: List<Stack<Char>>, moves: List<Move>): List<Stack<Char>> {
    for (move in moves) {
        val tempStack = mutableListOf<Char>()
        repeat(move.quantity) {
            tempStack.add(crateStacks[move.origin - 1].pop())
        }

        val reversedTempStack = tempStack.reversed().toMutableList()
        repeat(move.quantity) {
            crateStacks[move.destination - 1].push(reversedTempStack.first())
            reversedTempStack.removeFirst()
        }
    }
    return crateStacks
}

private fun createStacks(lines: List<String>): List<Stack<Char>> {
    val indexLineIndex = lines.indexOfFirst { it.startsWith(" 1 ") }
    val crateLines = lines.subList(fromIndex = 0, toIndex = indexLineIndex)
    val numberOfStacks = crateLines.reversed()[0].count { it.isLetter() }
    val stacks = List(numberOfStacks) { Stack<Char>() }
    for (line in crateLines.reversed()) {
        for (stackIndex in stacks.indices) {
            val c = line[(stackIndex * 4) + 1]
            if (c.isLetter()) {
                stacks[stackIndex].push(c)
            }
        }
    }
    return stacks
}

private fun createTopCratesString(crates: List<Stack<Char>>): String {
    return crates.filter { it.isNotEmpty() }
        .map { it.pop() }
        .joinToString("")
}
