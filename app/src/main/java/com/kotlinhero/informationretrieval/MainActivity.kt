package com.kotlinhero.informationretrieval

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.kotlinhero.informationretrieval.ui.screens.DocumentsScreen
import com.kotlinhero.informationretrieval.ui.theme.InformationRetrievalTheme
import com.kotlinhero.informationretrieval.ui.viewmodels.DocumentsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<DocumentsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InformationRetrievalTheme {
                val uiState by viewModel.stateFlow.collectAsState()
                DocumentsScreen(
                    uiState = uiState,
                    onChangeQuery = viewModel::onQueryChange,
                    onChangeDatasetType = viewModel::onDatasetTypeChange,
                    onSearchClick = viewModel::getDocuments
                )
            }
        }
    }
}