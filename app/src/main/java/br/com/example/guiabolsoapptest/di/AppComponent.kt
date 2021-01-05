package br.com.example.guiabolsoapptest.di

import br.com.example.guiabolsoapptest.repository.CategoriesRepository
import br.com.example.guiabolsoapptest.repository.JokesRepository
import br.com.example.guiabolsoapptest.view.BaseFragment
import br.com.example.guiabolsoapptest.view.ShareViewModel
import br.com.example.guiabolsoapptest.view.categories.CategoriesFragment
import br.com.example.guiabolsoapptest.viewmodel.CategoriesViewModel
import br.com.example.guiabolsoapptest.view.jokedetails.JokeDetailsFragment
import br.com.example.guiabolsoapptest.view.jokes.JokesFragment
import br.com.example.guiabolsoapptest.viewmodel.JokesViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    // Fragments
    fun inject(baseFragment: BaseFragment)
    fun inject(categoriesFragment: CategoriesFragment)
    fun inject(jokeDetailsFragment: JokeDetailsFragment)
    fun inject(jokesFragment: JokesFragment)

    // ViewModels
    fun inject(shareViewModel: ShareViewModel)
    fun inject(categoriesViewModel: CategoriesViewModel)
    fun inject(jokesViewModel: JokesViewModel)

    // Repositories
    fun inject(categoriesRepository: CategoriesRepository)
    fun inject(jokesRepository: JokesRepository)
}