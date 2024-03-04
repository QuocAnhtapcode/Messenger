package dev.proptit.messenger.contact

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.proptit.messenger.data.ApiService
import dev.proptit.messenger.data.Contact
import dev.proptit.messenger.data.RetrofitHelper
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ContactViewModel(application: Application): AndroidViewModel(application) {
    private val retrofitBuilder = RetrofitHelper.getInstance().create(ApiService::class.java)
    private val _contactList = MutableLiveData<List<Contact>>()
    init {
        viewModelScope.launch {
            _contactList.postValue(retrofitBuilder.getContact())
        }
    }
    internal suspend fun getContactById(id: Int): Contact = suspendCoroutine { continuation ->
        viewModelScope.launch {
            try{
                val contact = retrofitBuilder.getContactById(id)
                continuation.resume(contact)
            }catch(e: Exception){}
        }
    }
    val contactList: LiveData<List<Contact>> = _contactList
}