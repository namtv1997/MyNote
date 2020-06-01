package com.henrynam.mynote.domain


interface AuthenticationRepository {
    fun logged(onResult: () -> Unit)
}