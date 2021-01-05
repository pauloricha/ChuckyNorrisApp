package br.com.example.guiabolsoapptest.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import br.com.example.guiabolsoapptest.R
import br.com.example.guiabolsoapptest.repository.ShareRepository
import kotlinx.android.synthetic.main.fragment_categories.*

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeLiveData()
    }

    open fun init() {}
    open fun observeLiveData() {}

    fun observeLoading(repository: ShareRepository) {
        repository.isLoading.observe(viewLifecycleOwner, { isLoading ->
            isLoading.let {
                if (isLoading) {
                    loading.visibility = View.VISIBLE
                } else {
                    loading.visibility = View.GONE
                }
            }
        })
    }

    fun observeIsError(repository: ShareRepository) {
        repository.isError.observe(viewLifecycleOwner, { isError ->
            isError.let {
                Toast.makeText(activity, getString(R.string.error_connection),
                    Toast.LENGTH_LONG).show()
            }
        })
    }

    fun hideKeyboard(activity: FragmentActivity?) {
        try {
            val inputManager = activity
                ?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val currentFocusedView = activity.currentFocus
            if (currentFocusedView != null) {
                inputManager.hideSoftInputFromWindow(currentFocusedView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}