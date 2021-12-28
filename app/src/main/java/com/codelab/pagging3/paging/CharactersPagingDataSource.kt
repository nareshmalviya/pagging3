package com.metinozcura.rickandmorty.data.paging.datasource

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.codelab.pagging3.data.model.Characters
import com.codelab.pagging3.data.service.NetworkServices
import com.codelab.pagging3.data.service.RetrofitServiceApi



class CharactersPagingDataSource(private val service: RetrofitServiceApi) :
    PagingSource<Int, Characters>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
        val pageNumber = params.key ?: 1
        return try {
            val response = service.getAllCharacters(pageNumber)
            val pagedResponse = response.body()
            val data = pagedResponse?.results
            var nextPageNumber: Int? = null
            if (pagedResponse?.pageInfo?.next != null) {
                val uri = Uri.parse(pagedResponse.pageInfo.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Characters>): Int = 1
}