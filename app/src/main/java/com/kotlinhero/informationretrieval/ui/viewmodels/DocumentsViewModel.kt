package com.kotlinhero.informationretrieval.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinhero.informationretrieval.data.api.DocumentsApi
import com.kotlinhero.informationretrieval.data.enums.DatasetType
import com.kotlinhero.informationretrieval.data.exceptions.ServerException
import com.kotlinhero.informationretrieval.ui.state.DocumentsScreenUiState
import io.ktor.utils.io.printStack
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class DocumentsViewModel(private val documentsApi: DocumentsApi) : ViewModel() {
    private val _stateFlow = MutableStateFlow(DocumentsScreenUiState())
    val stateFlow = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _stateFlow
                .map { it.query }
                .distinctUntilChanged()
                .debounce(300)
                .collect { query ->
                    try {
                        val suggestions = documentsApi.getSuggestions(query, _stateFlow.first().datasetType.key)
                        _stateFlow.update {
                            it.copy(suggestions = suggestions)
                        }
                    } catch (ex: Exception) {
                        // TODO:
                    }
                }
        }
    }

    fun getDocuments() {
        viewModelScope.launch {
            try {
                _stateFlow.update { it.copy(isLoading = true) }
                val query = _stateFlow.value.query
                val datasetType = _stateFlow.value.datasetType
                val documents = documentsApi.getDocuments(
                    dataset = datasetType.key,
                    query = query
                )
                _stateFlow.update { it.copy(documents = documents) }
            } catch (ex: Exception) {
                ex.printStack()
                // TODO:
            } finally {
                _stateFlow.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onQueryChange(query: String) {
        _stateFlow.update {
            it.copy(query = query)
        }
    }

    fun onDatasetTypeChange(datasetType: DatasetType) {
        _stateFlow.update {
            it.copy(datasetType = datasetType)
        }
    }
}