package dev.proptit.messenger.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.proptit.messenger.data.ApiService
import dev.proptit.messenger.data.Register
import dev.proptit.messenger.data.RetrofitHelper
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val retrofitBuilder = RetrofitHelper.getInstance().create(ApiService::class.java)
    internal suspend fun postRegister(newRegister: Register):Int = suspendCoroutine{ continuation->
        viewModelScope.launch{
            try{
                val id = retrofitBuilder.postRegister(newRegister)
                continuation.resume(id)
            }catch (e: Exception){
                continuation.resume(-1)
            }
        }
    }
}

