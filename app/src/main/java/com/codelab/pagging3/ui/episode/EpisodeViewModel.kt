package com.codelab.pagging3.ui.episode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.codelab.pagging3.base.BaseViewModel
import com.codelab.pagging3.data.model.Episode
import com.codelab.pagging3.data.service.NetworkServices
import com.codelab.pagging3.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
@HiltViewModel
class EpisodeViewModel @Inject constructor(val networkServices: NetworkRepository) :BaseViewModel(){

    private lateinit var _episodesFlow: Flow<PagingData<Episode>>
    val episodesFlow: Flow<PagingData<Episode>> get() = _episodesFlow
    init {
        getAllEpisodes()
    }

    private fun getAllEpisodes() = launchPagingAsync({
        networkServices.getAllEpisodes().cachedIn(viewModelScope)
    }, {
        _episodesFlow = it
    })
}