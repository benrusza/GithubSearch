package com.brz.app.vm

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brz.app.model.GitHubUser
import com.brz.app.model.ResponseSearchUsers
import com.brz.app.retrofit.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GitHubUsersViewModel : ViewModel() {
    private val mResponse = MutableLiveData<List<GitHubUser>>()
    var gitHubUsers: LiveData<List<GitHubUser>> = mResponse
    var isLoading = MutableLiveData<Boolean>()
    var isLoaded = MutableLiveData<Boolean>()

    var errorMessage: String by mutableStateOf("")

    init {
        isLoading.value = false
        isLoaded.value = false
    }

    fun getGitHubUsers(input: String) {

        viewModelScope.launch(Dispatchers.IO) {

            if (input.length<2){
                return@launch
            }

            withContext(Dispatchers.Main){
                isLoading.value = true
                errorMessage = ""
            }

            delay(1000)
            try {
                val response = Client.service.getUsers(input).execute()
                if(response.isSuccessful){
                    withContext(Dispatchers.Main){
                        isLoading.value = false
                        response.body()?.let {
                            mResponse.value = it.items
                        }
                        gitHubUsers = mResponse
                    }

                }else{

                    if ( response.code() in 400..499) {
                        errorMessage = "Error in the request received from the client. "+response.code().toString()
                    } else if ( response.code() in 500..599) {
                        errorMessage = "The server encountered an issue and is not able to serve the client’s request. "+response.code().toString()
                    }else{
                        errorMessage = response.code().toString()
                    }

                }


            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = e.message.toString()
            }
        }
    }

}