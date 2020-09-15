package com.example.l4s_skill_webapi
import com.google.gson.annotations.SerializedName
import java.util.*

data class User (val name:String,
                 @SerializedName("login") val userId:String,
                 @SerializedName("avatar_url")val avatarUrl:String,
                 val following:Int,
                 val followers:Int)