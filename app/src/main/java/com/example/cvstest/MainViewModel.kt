package com.example.cvstest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val imageList = MutableLiveData<ImageResponse>()
    val loading = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            val response = RetrofitClient.client.getAllImages()
            if (response.isSuccessful) {
                imageList.postValue(response.body())
            }
        }
    }
}
