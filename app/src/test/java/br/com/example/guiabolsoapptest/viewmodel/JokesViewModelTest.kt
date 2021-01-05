package br.com.example.guiabolsoapptest.viewmodel

import br.com.example.guiabolsoapptest.model.Joke
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class JokesViewModelTest {

    val viewModel = JokesViewModel()
    val joke = Mockito.mock(Joke::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel.setJoke(joke)
    }

    @Test
    fun hasJoke() {
        assertTrue(viewModel.hasJoke()!!)
    }

    @Test
    fun isFavoriteJoke(){
        assertFalse(viewModel.isFavoriteJoke()!!)
    }
}