package com.kotlinhero.informationretrieval.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kotlinhero.informationretrieval.data.enums.DatasetType
import com.kotlinhero.informationretrieval.ui.reusable.DropDownPicker
import com.kotlinhero.informationretrieval.ui.reusable.SearchTextField
import com.kotlinhero.informationretrieval.ui.state.DocumentsScreenUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentsScreen(
    uiState: DocumentsScreenUiState,
    onChangeQuery: (String) -> Unit,
    onChangeDatasetType: (DatasetType) -> Unit,
    onSearchClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Text(text = "Information Retrieval")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onSearchClick) {
                when (uiState.isLoading) {
                    true -> CircularProgressIndicator(
                        modifier = Modifier.size(14.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    else -> Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp)
        ) {
            item {
                SearchTextField(
                    value = uiState.query,
                    onValueChange = onChangeQuery,
                    suggestions = uiState.suggestions.map { it.text },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                DropDownPicker(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.datasetType,
                    onValueChange = onChangeDatasetType,
                    values = listOf(
                        DatasetType.ANTIQUE,
                        DatasetType.LIFESTYLE,
                        DatasetType.ANTIQUE_CRAWLED,
                        DatasetType.LIFESTYLE_QUERIES
                    ),
                )
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            items(uiState.documents) { document ->
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = document.text,
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}