package com.codelab.pagging3.ui.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.codelab.pagging3.base.BaseViewModel
import com.codelab.pagging3.data.model.LocationModel
import com.codelab.pagging3.data.model.Locations
import com.codelab.pagging3.repository.NetworkRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(val networkRepository: NetworkRepository) : BaseViewModel() {

    private lateinit var _locationsFlow: Flow<PagingData<LocationModel>>
    val locationsFlow: Flow<PagingData<LocationModel>>
        get() = _locationsFlow

    init {
        getAllLocations()
    }

    private fun getAllLocations() = launchPagingAsync({
        networkRepository.getAllLocations()
    }, { flow ->
        _locationsFlow = flow.map { pagingData: PagingData<Locations> ->
            pagingData.map { location ->
                LocationModel.LocationData(location)
            }.insertSeparators<LocationModel.LocationData, LocationModel> { before, after ->
                when {
                    before == null -> null
                    after == null -> null
                    else -> LocationModel.LocationSeparator("Separator: $before-$after")
                }
            }
        }.cachedIn(viewModelScope)
    })
}