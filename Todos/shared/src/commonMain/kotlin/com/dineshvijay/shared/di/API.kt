package com.dineshvijay.shared.di

import com.dineshvijay.shared.data.api.TodosAPI
import com.dineshvijay.shared.data.network.HTTPService

class API {

    companion object {
        fun todosAPI(): TodosAPI = TodosAPI(HTTPService().client)
    }
}