package com.dineshvijay.shared.domain.repositories

import com.dineshvijay.shared.data.api.TodosAPI
import com.dineshvijay.shared.domain.entities.NewTodos
import com.dineshvijay.shared.domain.entities.Todos
import com.dineshvijay.shared.domain.entities.TodosError

interface TodosRepository {
    suspend fun addItem(item: NewTodos): Pair<Todos?, TodosError?>
    suspend fun updateItem(item: Todos): Pair<Todos?, TodosError?>
    suspend fun deleteItem(item: Todos): Pair<Todos?, TodosError?>
    suspend fun getItem(item: Todos): Pair<Todos?, TodosError?>
    suspend fun getAllItems(): Pair<List<Todos>?, TodosError?>
}