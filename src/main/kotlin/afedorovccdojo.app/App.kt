package afedorovccdojo.app

import kotlinx.coroutines.runBlocking

class App(
    private val practiceSelector: PracticeSelector,
    private val summaryDisplay: SummaryDisplay,
) {

    fun run() = runBlocking {
        while (true) {
            val practice = practiceSelector.pickPractice() ?: break

            println("Starting ${practice.name}")

            val summary = practice.execute()

            summaryDisplay.show(summary)
        }
    }
}