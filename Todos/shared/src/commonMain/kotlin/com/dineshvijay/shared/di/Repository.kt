package com.dineshvijay.shared.di

import com.dineshvijay.shared.data.respositories.TodosRepositoryImpl
import com.dineshvijay.shared.domain.repositories.TodosRepository

class Repository {

    companion object {
        fun todoRepository(): TodosRepositoryImpl = TodosRepositoryImpl(API.todosAPI())
    }
}