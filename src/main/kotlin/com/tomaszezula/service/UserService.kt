package com.tomaszezula.service

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email

typealias UserId = String

interface UserService {
    suspend fun currentUser(): UserId?
    suspend fun signUp(email: String, password: String)
    suspend fun signIn(email: String, password: String)
    suspend fun signOut()
}

class UserServiceImpl(
    private val client: SupabaseClient
) : UserService {
    override suspend fun currentUser(): UserId? =
        client.gotrue.currentSessionOrNull()?.user?.email

    override suspend fun signUp(email: String, password: String) {
        client.gotrue.signUpWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun signIn(email: String, password: String) {
        client.gotrue.loginWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun signOut() {
        client.gotrue.clearSession()
    }
}

