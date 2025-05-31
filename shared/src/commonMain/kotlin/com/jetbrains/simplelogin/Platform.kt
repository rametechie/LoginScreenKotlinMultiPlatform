package com.jetbrains.simplelogin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform