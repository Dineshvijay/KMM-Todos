package com.dineshvijay.shared

import io.ktor.client.features.logging.*

class KMLogger: Logger {
    override fun log(message: String) {
        print(message)
    }
}