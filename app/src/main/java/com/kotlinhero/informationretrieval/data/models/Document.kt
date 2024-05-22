package com.kotlinhero.informationretrieval.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Document(
    val id: String,
    val text: String,
)
