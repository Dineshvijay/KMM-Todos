package com.dineshvijay.shared.data.network

import com.dineshvijay.shared.KMLogger
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

class HTTPService {

    val client = HttpClient() {
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true

            }
            serializer = KotlinxSerializer(json)
        }
        install(Logging) {
            logger = KMLogger()
            level = LogLevel.ALL
        }
    }
}