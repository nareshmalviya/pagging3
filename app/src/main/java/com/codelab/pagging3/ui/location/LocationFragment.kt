package com.codelab.pagging3.ui.location

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
import com.codelab.pagging3.R
import com.codelab.pagging3.base.BaseFragment
import com.codelab.pagging3.databinding.FragmentLocationBinding
import com.codelab.pagging3.utils.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

@AndroidEntryPoint
class LocationFragment  : BaseFragment<FragmentLocationBinding, LocationViewModel>() {
    private val locationsViewModel by viewModels<LocationViewModel>()

    @Inject
    lateinit var locationAdapter: LocationAdapter

    override val layoutId: Int
        get() = R.layout.fragment_location

    override fun getVM(): LocationViewModel = locationsViewModel
    override fun bindVM(binding: FragmentLocationBinding, vm: LocationViewModel)= with(binding) {
        with(locationAdapter) {
            swipeRefresh.setOnRefreshListener { refresh() }
            rvLocations.adapter = withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(this),
                footer = PagingLoadStateAdapter(this)
            )
            with(vm) {
                launchOnLifecycleScope {
                    locationsFlow.collectLatest { submitData(it) }
                }
                launchOnLifecycleScope {
                    loadStateFlow.collectLatest {
                        swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                    }
                }
                launchOnLifecycleScope {
                    loadStateFlow.distinctUntilChangedBy { it.refresh }
                        .filter { it.refresh is LoadState.NotLoading }
                        .collect { rvLocations.scrollToPosition(0) }}
                }
            }
        }
    }