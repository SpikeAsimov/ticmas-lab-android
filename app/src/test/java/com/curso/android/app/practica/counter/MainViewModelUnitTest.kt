package com.curso.android.app.practica.counter
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import com.curso.android.app.practica.counter.view.MainActivity
import com.curso.android.app.practica.counter.view.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class MainViewModelUnitTest {

    private lateinit var viewModel: MainViewModel

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = MainViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun mainViewModel_CheckInitialValue() = runTest {
        val value = viewModel.counter.value?.number
        assertEquals(0, value)
    }

    @Test
    fun mainViewModel_TestIncrementValue() = runTest {
        launch {
            viewModel.incrementCounter()
        }
        advanceUntilIdle()
        val value = viewModel.counter.value?.number
        assertEquals(1, value)
    }

    @Test
    fun mainViewModel_TestIncrementValueTwice() = runTest {
        for (i in 0..1) {
            launch {
                viewModel.incrementCounter()
            }
            advanceUntilIdle()
        }
        val value = viewModel.counter.value?.number
        assertEquals(2, value)
    }

    @Test
    fun mainViewModel_TestDecrementValue() = runTest {
        launch {
            viewModel.decrementCounter()
        }
        advanceUntilIdle()
        val value = viewModel.counter.value?.number
        assertEquals(-1, value)
    }

    @Test
    fun testCompareTextsEqual() {
        viewModel.compareTexts("Hola", "Hola")
        assertEquals("Los textos son iguales.", viewModel.resultaLiveData.value)
    }

    @Test
    fun testCompareTextsNotEqual() {
        viewModel.compareTexts("Hola", "Chau")
        assertEquals("Los textos son diferentes.", viewModel.resultaLiveData.value)
    }


    //Test de UI

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testCompareButton() {
        onView(withId(R.id.editText1)).perform(typeText("Hola"))

        onView(withId(R.id.editText2)).perform(typeText("Hola"))

        closeSoftKeyboard()

        onView(withId(R.id.compareButton)).perform(click())

        onView(withId(R.id.resultTextView)).check(matches(withText("Los textos son iguales.")))
    }

}