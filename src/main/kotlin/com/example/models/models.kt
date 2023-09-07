package com.example.models


data class User(val userName:String,val password:String)
data class Register(val id:Int,val userName: String,val password: String,val gmail:String)
data class RegReceive(val userName: String,val password: String,val gmail:String)
data class Balance(val balance:Double)