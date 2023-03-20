package afedorovccdojo.app

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.BufferedWriter
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.util.*
import java.util.concurrent.Executors
import java.util.stream.Stream
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class StdinPracticeSelectorTest {
    private val coroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    private val pipedOutputStream = PipedOutputStream()
    private val pipedWriter = pipedOutputStream.bufferedWriter()
    private val pipedInputStream = PipedInputStream(pipedOutputStream)

    private val practiceFactories = listOf(
        PassthroughPracticeFactory(practice1, "description1"),
        PassthroughPracticeFactory(practice2, "description2")
    )

    companion object {
        private val practice1 = TestPractice("practice1")
        private val practice2 = TestPractice("practice2")

        @JvmStatic
        private fun testInput(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(0, null),
                Arguments.of(1, practice1),
                Arguments.of(2, practice2),
            )
        }
    }

    @AfterEach
    fun tearDown() {
        coroutineDispatcher.close()
        pipedWriter.close()
        pipedInputStream.close()
    }

    class TestPractice(override val name: String) : Practice {
        override fun execute(): Summary = Summary(name)
    }

    class PassthroughPracticeFactory(private val practice: Practice, private val description: String) : PracticeFactory {
        override fun create(): Practice = practice
        override fun describe(): String = description
    }

    @Test
    fun `handles negative index`() = runBlocking {
        val practiceSelector = StdinPracticeSelector(practiceFactories, Scanner(pipedInputStream))

        val deferred = async(coroutineDispatcher) { practiceSelector.pickPractice() }

        selectPractice(-1)

        assertTrue { deferred.await() == null }
    }


    @Test
    fun `handles missing index`() = runBlocking {
        val practiceSelector = StdinPracticeSelector(practiceFactories, Scanner(pipedInputStream))

        val deferred = async(coroutineDispatcher) { practiceSelector.pickPractice() }

        selectPractice(Int.MAX_VALUE)

        assertTrue { deferred.await() == null }
    }

    @Test
    fun `handles non-integer input`() = runBlocking {
        val practiceSelector = StdinPracticeSelector(practiceFactories, Scanner(pipedInputStream))

        val deferred = async(coroutineDispatcher) { practiceSelector.pickPractice() }

        pipedWriter.println("Hello")

        assertTrue { deferred.await() == null }
    }

    @ParameterizedTest
    @MethodSource("testInput")
    fun `selects practice`(index: Int, expectedPractice: Practice?) = runBlocking {
        val practiceSelector = StdinPracticeSelector(practiceFactories, Scanner(pipedInputStream))

        val deferred = async(coroutineDispatcher) { practiceSelector.pickPractice() }

        selectPractice(index)
        assertThat(deferred.await(), equalTo(expectedPractice))
    }

    private fun selectPractice(index: Int) {
        pipedWriter.println(index.toString())
    }

    private fun BufferedWriter.println(string: String) {
        write(string)
        newLine()
        flush()
    }
}