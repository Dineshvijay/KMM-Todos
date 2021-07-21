package com.dineshvijay.shared.data.api

import com.dineshvijay.shared.domain.entities.NewTodos
import com.dineshvijay.shared.domain.entities.Todos
import com.dineshvijay.shared.domain.entities.TodosError
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TodosAPI(private val httpClient: HttpClient ) {

    suspend fun addTodo(item: NewTodos): Pair<Todos?, TodosError?> {
        return try {
            val response: HttpResponse = httpClient.request("https://jsonplaceholder.typicode.com/todos") {
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    body = item
                }
            val stringBody: String = response.receive()
            val model = Json{ignoreUnknownKeys = true }.decodeFromString<Todos>(stringBody)
            Pair(model, null)
        } catch(cause: Throwable) {
            val error = TodosError(1000, cause.toString())
            Pair(null, error)
        }
    }

    suspend fun deleteTodo(item: Todos): Pair<Todos?, TodosError?> {
        return try {
            val response: HttpResponse = httpClient
                .request("https://jsonplaceholder.typicode.com/todos/${item.id}") {
                    method = HttpMethod.Delete
                    contentType(ContentType.Application.Json)
                    body = item
                }
            val stringBody: String = response.receive()
            val model = Json{ignoreUnknownKeys = true }.decodeFromString<Todos>(stringBody)
            Pair(model, null)
        } catch(cause: Throwable) {
            val error = TodosError(1000, cause.toString())
            Pair(null, error)
        }
    }

    suspend fun updateTodo(item: Todos): Pair<Todos?, TodosError?> {
        return try {
            val response: HttpResponse = httpClient
                .request("https://jsonplaceholder.typicode.com/todos/${item.id}") {
                    method = HttpMethod.Put
                    contentType(ContentType.Application.Json)
                    body = item
                }
            val stringBody: String = response.receive()
            val model = Json{ignoreUnknownKeys = true }.decodeFromString<Todos>(stringBody)
            Pair(model, null)
        } catch(cause: Throwable) {
            val error = TodosError(1000, cause.toString())
            Pair(null, error)
        }
    }

    suspend fun getTodoItem(item: Todos): Pair<Todos?, TodosError?> {
        return try {
            val response: HttpResponse = httpClient
                .request("https://jsonplaceholder.typicode.com/todos/${item.id}") {
                    method = HttpMethod.Get
                    contentType(ContentType.Application.Json)
                }
            val stringBody: String = response.receive()
            val model = Json{ignoreUnknownKeys = true }.decodeFromString<Todos>(stringBody)
            Pair(model, null)
        } catch(cause: Throwable) {
            val error = TodosError(1000, cause.toString())
            Pair(null, error)
        }
    }

    suspend fun getAllItems(): Pair<List<Todos>?, TodosError?> {
        return try {
            val response: HttpResponse = httpClient.request("https://jsonplaceholder.typicode.com/todos") {
                method = HttpMethod.Get
                contentType(ContentType.Application.Json)
            }
            val stringBody: String = response.receive()
            val model = Json{ignoreUnknownKeys = true }.decodeFromString<List<Todos>>(stringBody)
            Pair(model, null)
        } catch(cause: Throwable) {
            val error = TodosError(1000, cause.toString())
            Pair(null, error)
        }
    }
}