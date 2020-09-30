package com.example.gitapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class FollowerViewModel : ViewModel() {
    private val listUsergit = MutableLiveData<ArrayList<User>>()

    companion object {
        var DATA = "data"
    }

    fun setUser() {
        val listUser = ArrayList<User>()
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "766112021aff15a10db491638021665843d7b11d")
        client.addHeader("User-Agent", "application/json")
        client.addHeader("Accept", "application/json")
        val url = " https://api.github.com/users/$DATA/followers"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val dataObject = JSONArray(result)
                    for (i in 0 until dataObject.length()) {
                        val jsonObject = dataObject.getJSONObject(i)
                        val login = jsonObject.getString("login")
                        val url2 = "https://api.github.com/users/$login"
                        client.get(url2, object : AsyncHttpResponseHandler() {
                            override fun onSuccess(
                                statusCode: Int,
                                headers: Array<out Header>,
                                responseBody: ByteArray
                            ) {
                                val hasil = String(responseBody)
                                try {
                                    val jsonObjects = JSONObject(hasil)

                                    val userItems =
                                        User()
                                    userItems.avatar = jsonObjects.getString("avatar_url")
                                    userItems.name = jsonObjects.getString("name")
                                    userItems.username = jsonObjects.getString("login")
                                    userItems.location = jsonObjects.getString("location")
                                    userItems.repo = jsonObjects.getString("public_repos")
                                    userItems.company = jsonObjects.getString("company")
                                    userItems.followers = jsonObjects.getString("followers")
                                    userItems.following = jsonObjects.getString("following")
                                    userItems.blog = jsonObjects.getString("blog")
                                    listUser.add(userItems)
                                    //set data ke adapter

                                    listUsergit.postValue(listUser)

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

    fun getUser(): LiveData<ArrayList<User>> {
        return listUsergit
    }
}