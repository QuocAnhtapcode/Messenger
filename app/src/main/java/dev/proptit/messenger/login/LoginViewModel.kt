package dev.proptit.messenger.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.proptit.messenger.data.ApiService
import dev.proptit.messenger.data.Login
import dev.proptit.messenger.data.RetrofitHelper
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val retrofitBuilder = RetrofitHelper.getInstance().create(ApiService::class.java)
    //Sử dụng suspendCoroutine để chuyển từ hàm không đồng bộ thành đồng bộ
    internal suspend fun postLogin(login: Login): Int = suspendCoroutine { continuation ->
        viewModelScope.launch {//Bắt đầu 1 coroutine mới để thực hiện công việc không đồng bộ
            try {
                val id = retrofitBuilder.postLogin(login)
                continuation.resume(id)
                //Không có lỗi xảy ra thì trả về id
            } catch (e: Exception) {
                continuation.resume(-1)
                //Có lỗi thì trả về -1
            }
        }
    }
}
