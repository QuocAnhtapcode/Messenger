package dev.proptit.messenger.chats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.proptit.messenger.data.ApiService
import dev.proptit.messenger.data.Message
import dev.proptit.messenger.data.RetrofitHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MessageViewModel(application: Application):AndroidViewModel(application) {
    private val retrofitBuilder = RetrofitHelper.getInstance().create(ApiService::class.java)
    private val _message = MutableLiveData<List<Message>>()
    internal fun findMessageByContactId(id: Int){
        viewModelScope.launch{
            while (true){
                _message.postValue(retrofitBuilder.getMessageByContactId(id))
                delay(1000)//1 giây cập nhật tin nhắn 1 lần
            }
        }
    }
    internal fun addMessage(newMessage: Message){
        viewModelScope.launch {
            retrofitBuilder.postMessage(newMessage)
            _message.postValue(retrofitBuilder.getMessageByContactId(newMessage.idSendContact))
        }
    }
    val message: LiveData<List<Message>> = _message
}