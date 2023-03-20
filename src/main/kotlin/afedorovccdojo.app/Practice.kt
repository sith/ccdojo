package afedorovccdojo.app

interface Practice {
    val name: String

    fun execute(): Summary

}

data class Summary(val name: String) {

}
