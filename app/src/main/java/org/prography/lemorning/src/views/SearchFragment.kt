package org.prography.lemorning.src.views

import android.os.Bundle
import android.view.MotionEvent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentSearchBinding
import org.prography.lemorning.src.viewmodels.MainViewModel
import org.prography.lemorning.src.viewmodels.SearchViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel, MainViewModel>(R.layout.fragment_search) {

  override fun getViewModel(): SearchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

  override fun getParentViewModel(): MainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

  override fun initView(savedInstanceState: Bundle?) {
    binding.rvCategorySearch.adapter = vm.categoryAdapter

    binding.rvCategorySearch.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
      override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        if (e.action == MotionEvent.ACTION_UP) {
          showSimpleMessageDialog(getString(R.string.coming_soon))
        }
      }

      override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean = true
      override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) = Unit
    })

    binding.mbtnCloseSearch.setOnClickListener { findNavController().popBackStack() }
    binding.clSearchContainer.setOnClickListener { showSimpleMessageDialog(getString(R.string.coming_soon)) }

    vm.categoryList.observe(this) { vm.categoryAdapter.setItem(it) }
  }
}