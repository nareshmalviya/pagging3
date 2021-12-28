package com.codelab.pagging3.data.service

import androidx.paging.PagingData
import com.codelab.pagging3.data.model.Characters
import com.codelab.pagging3.data.model.Episode
import com.codelab.pagging3.data.model.Locations
import kotlinx.coroutines.flow.Flow

interface NetworkServices {

    fun getAllCharacters(): Flow<PagingData<Characters>>
    fun getAllEpisodes(): Flow<PagingData<Episode>>
    fun getAllLocations(): Flow<PagingData<Locations>>
}