package afedorovccdojo.app

class StdoutSummaryDisplay : SummaryDisplay {
    override fun show(summary: Summary) {
        println(summary)
    }
}