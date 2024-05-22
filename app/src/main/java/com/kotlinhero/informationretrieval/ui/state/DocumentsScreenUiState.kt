package com.kotlinhero.informationretrieval.ui.state

import com.kotlinhero.informationretrieval.data.enums.DatasetType
import com.kotlinhero.informationretrieval.data.models.Document
import com.kotlinhero.informationretrieval.data.models.Suggestion

data class DocumentsScreenUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val documents: List<Document> = emptyList(),
    val suggestions: List<Suggestion> = emptyList(),
    val datasetType: DatasetType = DatasetType.ANTIQUE,
)