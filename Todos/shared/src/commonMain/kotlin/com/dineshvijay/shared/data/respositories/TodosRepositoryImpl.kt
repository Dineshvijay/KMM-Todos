package com.dineshvijay.shared.data.respositories

import com.dineshvijay.shared.data.api.TodosAPI
import com.dineshvijay.shared.domain.entities.NewTodos
import com.dineshvijay.shared.domain.entities.Todos
import com.dineshvijay.shared.domain.entities.TodosError
import com.dineshvijay.shared.domain.repositories.TodosRepository

class TodosRepositoryImpl(private val source: TodosAPI): TodosRepository {
    override suspend fun addItem(item: NewTodos): Pair<Todos?, TodosError?> {
        return source.addTodo(item)
    }

    override suspend fun updateItem(item: Todos): Pair<Todos?, TodosError?> {
        return source.updateTodo(item)
    }

    override suspend fun deleteItem(item: Todos): Pair<Todos?, TodosError?> {
        return source.deleteTodo(item)
    }

    override suspend fun getItem(item: Todos): Pair<Todos?, TodosError?> {
        return source.getTodoItem(item)
    }

    override suspend fun getAllItems(): Pair<List<Todos>?, TodosError?> {
        return source.getAllItems()
    }
}