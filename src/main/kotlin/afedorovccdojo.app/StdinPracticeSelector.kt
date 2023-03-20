package afedorovccdojo.app

import java.util.Scanner

class StdinPracticeSelector(private val practiceFactories: List<PracticeFactory>, private val scanner: Scanner) : PracticeSelector {
    override suspend fun pickPractice(): Practice? {
        practiceFactories.print()

        val index = scanner.nextInt()
        println("Got $index")
        if (index == 0) {
            return null
        }
        return practiceFactories[index - 1].create()
    }

    private fun List<PracticeFactory>.print() {
        forEachIndexed { index, practiceFactory ->
            println("${index + 1}. ${practiceFactory.describe()}")
        }
        println("0 - Exit")
    }
}