package afedorovccdojo.app

import kotlinx.coroutines.runBlocking

class App(
    private val practiceSelector: PracticeSelector,
    private val summaryDisplay: SummaryDisplay,
) {

    fun run() = runBlocking {
        while (true) {
            val practice = practiceSelector.pickPractice() ?: break

            val summary = practice.execute()

            summaryDisplay.show(summary)
        }
    }
}