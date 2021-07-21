package com.dineshvijay.shared.viewModels

import com.dineshvijay.shared.domain.entities.NewTodos
import com.dineshvijay.shared.domain.entities.Todos
import com.dineshvijay.shared.domain.entities.TodosError
import com.dineshvijay.shared.domain.usecases.TodosUseCase
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.utils.io.*
import io.ktor.utils.io.core.internal.*
import kotlinx.coroutines.launch
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

class TodosViewModel(private val useCase: TodosUseCase, val eventsDispatcher: EventsDispatcher<TodosViewModel.TodosModelEvents>): ViewModel() {

    interface TodosModelEvents {
        fun receivedAllTodos(todos: List<Todos>){}
        fun errorMessage(error: String?)
        fun updatedTodos(){}
    }

    private lateinit var todosList: MutableList<Todos>
     fun getAllTodos() {
        viewModelScope.launch {
            val (todos, error) = useCase.getAllTodos()
            if(todos != null) {
                todosList = todos.toMutableList()
                eventsDispatcher.dispatchEvent { receivedAllTodos(todos)}
            } else {
                eventsDispatcher.dispatchEvent { errorMessage(error?.reason.toString()) }
            }
        }
    }

    fun updateTodos(todo: Todos) {
        viewModelScope.launch {
            val (todo, error) = useCase.updateTodo(todo)
            eventsDispatcher.dispatchEvent {  updatedTodos() }
        }
    }

    fun addTodo(title: String) {
        val todo = NewTodos(1, title)
        viewModelScope.launch {
            val (todos, error) = useCase.addNewTodo(todo)
            if(todos != null) {
                eventsDispatcher.dispatchEvent { updatedTodos() }
            } else {
                eventsDispatcher.dispatchEvent { errorMessage(error?.reason.toString()) }
            }
        }
    }
}