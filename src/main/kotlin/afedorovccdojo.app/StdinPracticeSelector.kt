package afedorovccdojo.app

import java.util.Scanner

class StdinPracticeSelector(private val practiceFactories: List<PracticeFactory>, private val scanner: Scanner) : PracticeSelector {
    override suspend fun pickPractice(): Practice? {
        practiceFactories.print()

        try {
            val index = scanner.nextInt()
            if (index == 0) {
                return null
            }

            if (index < 0) {
                System.err.println("Negative index provided. Exiting")
                return null
            }

            if (index > practiceFactories.size) {
                System.err.println("Index $index is greater than size of practices. Exiting")
                return null
            }

            return practiceFactories[index - 1].create()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun List<PracticeFactory>.print() {
        forEachIndexed { index, practiceFactory ->
            println("${index + 1}. ${practiceFactory.describe()}")
        }
        println("0 - Exit")
    }
}