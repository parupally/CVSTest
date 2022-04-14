package com.example.cvstest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvstest.model.ImageResponse
import com.example.cvstest.data.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val imageList = MutableLiveData<ImageResponse>()
    val loading = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch(Dispatchers.IO){
            loading.postValue(true)
            val response = RetrofitClient.client.getAllImages()
            if (response.isSuccessful) {
                loading.postValue(false)
                imageList.postValue(response.body())
            }
            else {
                loading.postValue(false)
                errorMessage.postValue(response.message())
            }
        }
    }
}
