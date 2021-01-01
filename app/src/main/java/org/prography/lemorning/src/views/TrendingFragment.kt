package org.prography.lemorning.src.views

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentTrendingBinding
import org.prography.lemorning.src.viewmodels.MainViewModel
import org.prography.lemorning.src.viewmodels.TrendingViewModel

class TrendingFragment
    : BaseFragment<FragmentTrendingBinding, TrendingViewModel, MainViewModel>(R.layout.fragment_trending) {

    override fun getViewModel(): TrendingViewModel =
        ViewModelProvider(this).get(TrendingViewModel::class.java)

    override fun getParentViewModel(): MainViewModel =
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        binding.rvForyouTrending.adapter = vm.forYouAdapter
        binding.rvPopularTrending.adapter = vm.popularAdapter

        binding.mbtnPeriodPopularTrending.setOnClickListener { showSimpleMessageDialog(getString(R.string.coming_soon)) }
        binding.clSearchContainer.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.clSearchContainer to "search_box",
                binding.ivSearchIcon to "search_icon"
            )
            findNavController().navigate(R.id.action_to_search, null, null, extras)
        }

        /* Data Observing */
        pvm.songs.observe(this) { vm.forYouAdapter.setItem(it) }
        pvm.songs.observe(this) { vm.popularAdapter.setItem(it) }

        vm.moveToSong.observe(this) {
            it.get()?.let {
                val intent = Intent(activity, SongDetailActivity::class.java)
                intent.putExtra("songNo", it)
                startActivity(
                    Intent(
                        activity,
                        SongDetailActivity::class.java
                    ).apply { putExtra("songNo", it) })
            }
        }
    }
}