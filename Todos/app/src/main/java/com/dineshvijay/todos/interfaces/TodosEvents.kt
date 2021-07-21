package com.dineshvijay.todos.interfaces

import android.view.View
import com.dineshvijay.shared.domain.entities.Todos

interface TodosEvents {
    fun selectedTodos(view: View, todos: Todos)
    fun setTodosStatus(todos: Todos, atIndex: Int)
}