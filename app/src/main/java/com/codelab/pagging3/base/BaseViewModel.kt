package com.codelab.pagging3.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelab.pagging3.utils.SingleLiveEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseViewModel :ViewModel() {
    var errorMessage = SingleLiveEvent<String>()
    var progressLiveEvent = SingleLiveEvent<Boolean>()

    inline fun <T> launchPagingAsync(
        crossinline execute: suspend () -> Flow<T>,
        crossinline onSuccess: (Flow<T>) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = execute()
                onSuccess(result)
            } catch (ex: Exception) {
                errorMessage.value = ex.message
            }
        }
    }
}