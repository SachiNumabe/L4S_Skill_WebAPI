package com.example.l4s_skill_webapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import coil.api.load
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gson: Gson =
            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val userService: UserService = retrofit.create(UserService::class.java)

        requestButton.setOnClickListener {

            runBlocking(Dispatchers.IO) {
                runCatching {
                    userService.getUser("lifeistech")
                }
            }.onSuccess{
                avatarImageView.load(it.avatarUrl)
                nameTextView.text = it.name
                userIdTextView.text = it.userId
                followingTextView.text = it.following.toString()
                followersTextView.text = it.followers.toString()
                Toast.makeText(this,"成功", Toast.LENGTH_LONG).show()
            }.onFailure{
                Toast.makeText(this,"失敗", Toast.LENGTH_LONG).show()
            }
        }
    }
}