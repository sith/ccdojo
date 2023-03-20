package afedorovccdojo.app

interface PracticeSelector {
    suspend fun pickPractice(): Practice?
}