package com.codelab.pagging3.ui.charcters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.codelab.pagging3.base.BaseViewModel
import com.codelab.pagging3.data.model.Characters
import com.codelab.pagging3.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
@HiltViewModel
class CharctersViewModel @Inject constructor(val networkRepository: NetworkRepository) : BaseViewModel() {

    private lateinit var _charactersFlow: Flow<PagingData<Characters>>
    val charactersFlow: Flow<PagingData<Characters>>
        get() = _charactersFlow

    init {
        getAllCharacters()
    }

    private fun getAllCharacters() = launchPagingAsync({
        networkRepository.getAllCharacters().cachedIn(viewModelScope)
    }, {
        _charactersFlow = it
    })

}