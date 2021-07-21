package com.dineshvijay.shared.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class NewTodos(val userId: Int,
                    var title: String)