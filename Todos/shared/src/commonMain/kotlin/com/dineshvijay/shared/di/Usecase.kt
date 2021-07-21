package com.dineshvijay.shared.di

import com.dineshvijay.shared.domain.usecases.TodosUseCase

class Usecase {

    companion object {
        fun todos(): TodosUseCase = TodosUseCase(Repository.todoRepository())
    }
}