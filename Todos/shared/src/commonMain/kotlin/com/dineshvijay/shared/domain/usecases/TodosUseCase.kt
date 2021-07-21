package com.dineshvijay.shared.domain.usecases

import com.dineshvijay.shared.domain.entities.NewTodos
import com.dineshvijay.shared.domain.entities.Todos
import com.dineshvijay.shared.domain.entities.TodosError
import com.dineshvijay.shared.domain.repositories.TodosRepository

class TodosUseCase(private val repository: TodosRepository) {

    suspend fun addNewTodo(item: NewTodos): Pair<Todos?, TodosError?> {
        return repository.addItem(item)
    }
    suspend fun updateTodo(item: Todos): Pair<Todos?, TodosError?> {
        return repository.updateItem(item)
    }
    suspend fun deleteTodo(item: Todos): Pair<Todos?, TodosError?> {
        return repository.deleteItem(item)
    }
    suspend fun getTodo(item: Todos): Pair<Todos?, TodosError?> {
        return repository.getItem(item)
    }
    suspend fun getAllTodos(): Pair<List<Todos>?, TodosError?> {
        return repository.getAllItems()
    }
}