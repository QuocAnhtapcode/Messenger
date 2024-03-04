package dev.proptit.messenger.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("message/{id}")
     suspend fun getMessageByContactId(@Path("id") id: Int): List<Message>

    @POST("message/create")
    suspend fun postMessage(@Body requestBody: Message): Int

    @GET("contact")
    suspend fun getContact(): List<Contact>

    @GET("contact/{id}")
    suspend fun getContactById(@Path("id") id:Int): Contact

    @POST("contact/login")
    suspend fun postLogin(@Body requestBody: Login): Int

    @POST("contact/register")
    suspend fun postRegister(@Body requestBody: Register): Int
}