package com.example.gitapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private val listUsergit = MutableLiveData<ArrayList<User>>()

    fun setUser(users: String) {
        val listItems = ArrayList<User>()

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "766112021aff15a10db491638021665843d7b11d")
        client.addHeader("User-Agent", "application/json")
        client.addHeader("Accept", "application/json")
        val url = " https://api.github.com/search/users?q=$users"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    //parsing json
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("items")
                    for (i in 0 until list.length()) {
                        val dataObject = list.getJSONObject(i)
                        val login = dataObject.getString("login")
                        val url2 = "https://api.github.com/users/$login"
                        client.get(url2, object : AsyncHttpResponseHandler() {
                            override fun onSuccess(
                                statusCode: Int,
                                headers: Array<out Header>,
                                responseBody: ByteArray
                            ) {
                                val hasil = String(responseBody)
                                try {
                                    val jsonObject = JSONObject(hasil)

                                    val userItem =
                                        User()
                                    userItem.avatar = jsonObject.getString("avatar_url")
                                    userItem.name = jsonObject.getString("name")
                                    userItem.username = jsonObject.getString("login")
                                    userItem.location = jsonObject.getString("location")
                                    userItem.repo = jsonObject.getString("public_repos")
                                    userItem.company = jsonObject.getString("company")
                                    userItem.followers = jsonObject.getString("followers")
                                    userItem.following = jsonObject.getString("following")
                                    listItems.add(userItem)
                                    //set data ke adapter

                                    listUsergit.postValue(listItems)

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }

                            override fun onFailure(
                                statusCode: Int,
                                headers: Array<Header>,
                                responseBody: ByteArray,
                                error: Throwable
                            ) {
                                Log.d("onFailure", error.message.toString())
                            }

                        })
                    }

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getUser(): LiveData<ArrayList<User>> {
        return listUsergit
    }
}