package afedorovccdojo.app

interface Practice {
    fun execute(): Summary

}

data class Summary(val name: String) {

}
