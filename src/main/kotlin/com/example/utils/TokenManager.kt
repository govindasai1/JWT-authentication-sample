package com.example.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.models.Register
import java.util.*

class TokenManager {
    private val secret = "secret"
    private val issuer = "http://0.0.0.0:8079/"
    private val audience = "http://0.0.0.0:8079/hello"
    val realm = "Access to 'hello'"
    fun generateJWTToken(register: Register): String {
        val expireDate = System.currentTimeMillis() + 600000
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("userName", register.userName)
            .withExpiresAt(Date(expireDate))
            .sign(Algorithm.HMAC256(secret))
    }
    fun verifyJWTToken():JWTVerifier{
        return JWT.require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()
    }
}