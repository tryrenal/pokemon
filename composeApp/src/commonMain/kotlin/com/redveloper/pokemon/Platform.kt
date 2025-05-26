package com.redveloper.pokemon

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform