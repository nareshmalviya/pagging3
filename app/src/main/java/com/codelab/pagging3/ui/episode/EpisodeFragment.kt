package com.codelab.pagging3.ui.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.codelab.pagging3.R
import com.codelab.pagging3.base.BaseFragment
import com.codelab.pagging3.databinding.FragmentEpisodeBinding
import com.codelab.pagging3.utils.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

@AndroidEntryPoint
class EpisodeFragment : BaseFragment<FragmentEpisodeBinding,EpisodeViewModel>() {
     val episodesViewModel  by viewModels<EpisodeViewModel>()
  /*  private val episodesViewModel by lazy {
        ViewModelProvider(this).get(EpisodeViewModel::class.java)
    }*/

    @Inject
    lateinit var episodeAdapter: EpisodeAdapter

    override val layoutId: Int
        get() = R.layout.fragment_episode

    override fun getVM(): EpisodeViewModel = episodesViewModel

    override fun bindVM(binding: FragmentEpisodeBinding, vm: EpisodeViewModel) = with(binding){

        with(episodeAdapter){
            swipeRefresh.setOnRefreshListener { refresh() }
            rvEpisodes.adapter = withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(this),
                footer = PagingLoadStateAdapter(this)
            )

            with(vm){
                launchOnLifecycleScope { episodesFlow.collect { submitData(it) } }
            }
            launchOnLifecycleScope {
                loadStateFlow.collectLatest {
                    swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                }
            }
            launchOnLifecycleScope {
                loadStateFlow.distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect { rvEpisodes.scrollToPosition(0) }
            }

        }
    }


}