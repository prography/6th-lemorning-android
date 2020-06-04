package org.prography.lemorning.src.view

import android.transition.Transition
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentSearchBinding
import org.prography.lemorning.src.viewmodel.SearchViewModel

class SearchFragment(override val layoutId: Int = R.layout.fragment_search) : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    override fun getViewModel(): SearchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

    override fun initView() {

        binding.mbtnCloseSearch.setOnClickListener { NavHostFragment.findNavController(this).popBackStack() }
    }
}