package afedorovccdojo.app

import kotlinx.coroutines.runBlocking
import java.util.*

fun main(args: Array<String>) = runBlocking {
    println("Welcome to CharaChorder Dojo!")

    Runtime.getRuntime().addShutdownHook(Thread() {
        println("Practice is over")
    })

    val practiceSelector = StdinPracticeSelector(listOf(), Scanner(System.`in`))


    val summaryDisplay = StdoutSummaryDisplay()


    App(practiceSelector, summaryDisplay).run()

}