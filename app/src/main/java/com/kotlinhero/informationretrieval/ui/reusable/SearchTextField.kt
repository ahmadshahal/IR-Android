package com.kotlinhero.informationretrieval.ui.reusable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    suggestions: List<String>,
    modifier: Modifier = Modifier,
) {
    var dropDownMenuExpanded by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(key1 = suggestions) {
        if(suggestions.isNotEmpty()) {
            dropDownMenuExpanded = true
        }
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = dropDownMenuExpanded,
        onExpandedChange = { dropDownMenuExpanded = it },
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            label = {
                Text(text = "What are you looking for?")
            },
            maxLines = 3,
            suffix = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
        )
        ExposedDropdownMenu(
            modifier = Modifier.heightIn(max = 140.dp),
            expanded = dropDownMenuExpanded && suggestions.isNotEmpty(),
            onDismissRequest = { dropDownMenuExpanded = false },
        ) {
            suggestions.forEach {
                DropdownMenuItem(
                    onClick = {
                        onValueChange(it)
                        dropDownMenuExpanded = false
                    },
                    text = {
                        Text(text = it)
                    },
                )
            }
        }
    }
}