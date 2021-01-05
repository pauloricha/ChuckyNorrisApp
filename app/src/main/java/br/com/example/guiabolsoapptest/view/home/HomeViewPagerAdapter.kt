package br.com.example.guiabolsoapptest.view.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.example.guiabolsoapptest.view.categories.CategoriesFragment
import br.com.example.guiabolsoapptest.view.jokes.JokesFragment

const val CATEGORIES_PAGE_INDEX = 0
const val JOKES_PAGE_INDEX = 1

class HomeViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        CATEGORIES_PAGE_INDEX to { CategoriesFragment() },
        JOKES_PAGE_INDEX to { JokesFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}
