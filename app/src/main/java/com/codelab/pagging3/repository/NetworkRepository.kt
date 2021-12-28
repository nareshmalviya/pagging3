package com.codelab.pagging3.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.codelab.pagging3.data.model.Characters
import com.codelab.pagging3.data.model.Episode
import com.codelab.pagging3.data.model.Locations
import com.codelab.pagging3.data.service.NetworkServices
import com.codelab.pagging3.data.service.RetrofitServiceApi
import com.metinozcura.rickandmorty.data.paging.datasource.CharactersPagingDataSource
import com.metinozcura.rickandmorty.data.paging.datasource.EpisodesPagingDataSource
import com.metinozcura.rickandmorty.data.paging.datasource.LocationPagingDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkRepository @Inject constructor(val retrofitServiceApi: RetrofitServiceApi):NetworkServices {
    override fun getAllCharacters(): Flow<PagingData<Characters>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { CharactersPagingDataSource(retrofitServiceApi) }
    ).flow


    override fun getAllEpisodes(): Flow<PagingData<Episode>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { EpisodesPagingDataSource(retrofitServiceApi) }
    ).flow


    override fun getAllLocations(): Flow<PagingData<Locations>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { LocationPagingDataSource(retrofitServiceApi) }
    ).flow

}