package com.kotlinhero.informationretrieval.ui.reusable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropDownPicker(
    value: T,
    onValueChange: (T) -> Unit,
    values: List<T>,
    modifier: Modifier = Modifier,
) {
    var dropDownMenuExpanded by rememberSaveable { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = dropDownMenuExpanded,
        onExpandedChange = { dropDownMenuExpanded = it },
    ) {
        OutlinedTextField(
            value = value.toString(),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            label = {
                Text(text = "Dataset")
            },
            maxLines = 3,
            readOnly = true,
            suffix = {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
        )
        ExposedDropdownMenu(
            expanded = dropDownMenuExpanded,
            onDismissRequest = { dropDownMenuExpanded = false },
        ) {
            values.forEach {
                DropdownMenuItem(
                    onClick = {
                        onValueChange(it)
                        dropDownMenuExpanded = false
                    },
                    text = {
                        Text(
                            text = it.toString(),
                        )
                    },
                )
            }
        }
    }
}