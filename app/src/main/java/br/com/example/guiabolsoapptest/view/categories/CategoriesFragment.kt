package br.com.example.guiabolsoapptest.view.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.example.guiabolsoapptest.R
import br.com.example.guiabolsoapptest.di.DaggerAppComponent
import br.com.example.guiabolsoapptest.internal.BUNDLE_CATEGORY
import br.com.example.guiabolsoapptest.internal.BUNDLE_TEXT_TO_SEARCH
import br.com.example.guiabolsoapptest.view.BaseFragment
import br.com.example.guiabolsoapptest.viewmodel.CategoriesViewModel
import kotlinx.android.synthetic.main.fragment_categories.*
import javax.inject.Inject

class CategoriesFragment : BaseFragment() {

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    private val viewModel = CategoriesViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun init() {
        DaggerAppComponent.create().inject(this)

        btnSearchJokes.setOnClickListener {
            val text = edtSearchJokes.text.toString()
            if(!text.isEmpty()){
                navigateToFoundJokes(edtSearchJokes.text.toString())
            } else {
                Toast.makeText(activity, getString(R.string.txt_toast_search_jokes),
                    Toast.LENGTH_SHORT).show()
            }
            edtSearchJokes.text.clear()
            hideKeyboard(activity)
        }

        rvCategories.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoryAdapter
        }

        categoryAdapter.onItemClick = { position ->
            navigateToJokeDetails(position)
        }
    }

    override fun observeLiveData() {
        observeLoading(viewModel.categoriesRepository)
        observeIsError(viewModel.categoriesRepository)
        observeCategories()
    }

    private fun observeCategories() {
        viewModel.categoriesRepository.categories.observe(viewLifecycleOwner, { categories ->
            categories.let {
                edtSearchJokes.visibility = View.VISIBLE
                btnSearchJokes.visibility = View.VISIBLE
                rvCategories.visibility = View.VISIBLE
                categoryAdapter.update(it)
            }
        })
    }

    private fun navigateToJokeDetails(position: Int) {
        val category = viewModel.categoriesRepository.categories.value?.get(position)
        val bundle = bundleOf(BUNDLE_CATEGORY to category)
        findNavController().navigate(
            R.id.action_view_pager_fragment_to_joke_details_fragment,
            bundle
        )
    }

    private fun navigateToFoundJokes(textToSearch: String) {
        val bundle = bundleOf(BUNDLE_TEXT_TO_SEARCH to textToSearch)
        findNavController().navigate(
            R.id.action_view_pager_fragment_to_jokes_fragment,
            bundle
        )
    }
}