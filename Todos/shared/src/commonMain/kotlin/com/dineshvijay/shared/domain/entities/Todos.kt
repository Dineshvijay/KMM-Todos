package com.dineshvijay.shared.domain.entities

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable data class Todos(val id: Int,
                               val userId: Int,
                               var title: String,
                               var completed: Boolean? = null): Parcelable