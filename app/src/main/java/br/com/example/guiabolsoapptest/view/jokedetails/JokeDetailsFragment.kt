package br.com.example.guiabolsoapptest.view.jokedetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.example.guiabolsoapptest.R
import br.com.example.guiabolsoapptest.databinding.FragmentJokeDetailsBinding
import br.com.example.guiabolsoapptest.di.DaggerAppComponent
import br.com.example.guiabolsoapptest.model.Joke
import br.com.example.guiabolsoapptest.view.BaseFragment
import br.com.example.guiabolsoapptest.viewmodel.JokesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_jokes.*

class JokeDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentJokeDetailsBinding

    private val viewModel = JokesViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJokeDetailsBinding.inflate(inflater, container, false)
        viewModel.checkArguments(arguments)

        return binding.root
    }

    override fun init() {
        DaggerAppComponent.create().inject(this)

        setToolbar()

        binding.imgFavorite.setOnClickListener {
            with(viewModel) {
                if(hasJoke()!!){
                    if(!isFavoriteJoke()!!) {
                        viewModel.insertJokeIntoDatabase(getJoke()!!)
                    } else {
                        viewModel.deleteJokeFromDatabase(getJoke()!!)
                    }
                } else {
                    viewModel.insertJokeIntoDatabase(getJoke()!!)
                }
            }
        }

        binding.tvLink.setOnClickListener {
            val url = viewModel.getJoke()?.url
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        with(viewModel) {
            if (hasJoke()!!) {
                setView(getJoke()!!)
                if(isFavoriteJoke()!!){
                    binding.imgFavorite.setBackgroundResource(R.drawable.ic_favorite)
                    binding.tvInfo.text = getString(R.string.txt_click_to_dislike)
                }
            } else {
                viewModel.fetchJokeFromApi(getCategory()!!)
            }
        }
    }

    override fun observeLiveData() {
        observeLoading(viewModel.jokesRepository)
        observeIsError(viewModel.jokesRepository)
        observeJoke()
        observeFavoriteJoke()
        observeIsDeleted()
    }

    fun setToolbar() {
        toolbar.visibility = View.VISIBLE
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            activity?.onBackPressed()
        })
    }

    private fun setView(joke: Joke) {
        binding.loading.visibility = View.GONE
        binding.visible = true
        binding.joke = joke
    }

    private fun observeJoke() {
        viewModel.jokesRepository.joke.observe(viewLifecycleOwner, { joke ->
            joke.let {
                viewModel.setJoke(joke)
                binding.visible = true
                binding.joke = joke
            }
        })
    }

    private fun observeFavoriteJoke() {
        viewModel.jokesRepository.isFavoriteJoke.observe(viewLifecycleOwner, {
            binding.imgFavorite.setBackgroundResource(R.drawable.ic_favorite)
            binding.tvInfo.text = getString(R.string.txt_click_to_dislike)
            Snackbar.make(binding.root, R.string.favorite_joke_added, Snackbar.LENGTH_LONG).show()
        })
    }

    private fun observeIsDeleted() {
        viewModel.jokesRepository.isDeleted.observe(viewLifecycleOwner, {
            viewModel.getJoke()!!.isFavorite = false
            binding.imgFavorite.setBackgroundResource(R.drawable.ic_dislike)
            binding.tvInfo.text = getString(R.string.txt_click_to_like)
            Snackbar.make(binding.root, R.string.favorite_joke_removed, Snackbar.LENGTH_LONG).show()
        })
    }
}