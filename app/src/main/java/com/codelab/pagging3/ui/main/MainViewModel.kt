package com.codelab.pagging3.ui.main

import com.codelab.pagging3.base.BaseViewModel
import com.codelab.pagging3.data.service.NetworkServices
import com.codelab.pagging3.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(val networkServices: NetworkRepository)  : BaseViewModel(){

}