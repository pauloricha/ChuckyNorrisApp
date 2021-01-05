package br.com.example.guiabolsoapptest.view.jokes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import br.com.example.guiabolsoapptest.R
import br.com.example.guiabolsoapptest.data.database.JokeSingleton
import br.com.example.guiabolsoapptest.di.DaggerAppComponent
import br.com.example.guiabolsoapptest.internal.BUNDLE_JOKE
import br.com.example.guiabolsoapptest.model.Joke
import br.com.example.guiabolsoapptest.view.BaseFragment
import br.com.example.guiabolsoapptest.view.home.CATEGORIES_PAGE_INDEX
import br.com.example.guiabolsoapptest.viewmodel.JokesViewModel
import kotlinx.android.synthetic.main.fragment_jokes.*

class JokesFragment : BaseFragment(), JokeAdapterClickListener {

    private val viewModel = JokesViewModel()

    private val jokeAdapter = JokeAdapter(
        arrayListOf(), true,
        this
    )

    var v: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_jokes, container, false)
            viewModel.checkArguments(arguments)
        }
        return v
    }

    override fun init() {
        DaggerAppComponent.create().inject(this)

        rvJokes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = jokeAdapter
            jokeAdapter.onItemClick = { position ->
                navigateToJokeDetails(position)
            }
        }

        with(viewModel) {
            if (!isFavoriteScreen()!!) {
                setToolbar()
                viewModel.fetchFoundJokesFromApi(getTextToSearch()!!)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(viewModel) {
            if (isFavoriteScreen()!!) {
                viewModel.fetchFavoriteJokesFromDatabase()
            }
        }
    }

    override fun observeLiveData() {
        observeLoading(viewModel.jokesRepository)
        observeIsError(viewModel.jokesRepository)
        observeJokes()
        observeFavoriteJokes()
        observeIsDeleted()
    }

    fun setToolbar() {
        toolbar.visibility = View.VISIBLE
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            activity?.onBackPressed()
        })
    }

    private fun observeJokes() {
        viewModel.jokesRepository.jokes.observe(viewLifecycleOwner, { jokes ->
            jokes.let {
                if(!jokes.isEmpty()) {
                    rvJokes.visibility = View.VISIBLE
                    jokeAdapter.update(jokes, viewModel.isFavoriteScreen())
                }
            }
        })
    }

    private fun observeFavoriteJokes() {
        viewModel.jokesRepository.favoriteJokes.observe(viewLifecycleOwner, { favoriteJokes ->
            favoriteJokes.let {
                if(!favoriteJokes.isEmpty()) {
                    rvJokes.visibility = View.VISIBLE
                    jokeAdapter.update(favoriteJokes, viewModel.isFavoriteScreen())
                } else {
                    jokeAdapter.update(favoriteJokes, viewModel.isFavoriteScreen())
                    requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
                        CATEGORIES_PAGE_INDEX
                }
            }
        })
    }

    private fun observeIsDeleted() {
        viewModel.jokesRepository.isDeleted.observe(viewLifecycleOwner, { it ->
            it.let {
                viewModel.fetchFavoriteJokesFromDatabase()
            }
        })
    }

    private fun navigateToJokeDetails(position: Int) {
        var joke: Joke?

        if (viewModel.isFavoriteScreen()!!) {
            joke = viewModel.jokesRepository.favoriteJokes.value?.get(position)
        } else {
            joke = viewModel.jokesRepository.jokes.value?.get(position)
        }

        val findedJoke = JokeSingleton.favoriteJokes.find {
            it.id == joke?.id
        }

        if (findedJoke != null) {
            joke = findedJoke
        }

        val bundle = bundleOf(BUNDLE_JOKE to joke)
        findNavController().navigate(
            R.id.action_view_pager_fragment_to_joke_details_fragment,
            bundle
        )
    }

    override fun onJokeAdapterClickListener(joke: Joke) {
        viewModel.deleteJokeFromDatabase(joke)
    }
}