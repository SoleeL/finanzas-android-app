package com.soleel.finanzas.core.ui.template

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.soleel.finanzas.core.common.eventmanager.SingleEventManager
import com.soleel.finanzas.core.ui.util.onSingleClick
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun <T> LargeDropdownMenu(
    modifier: Modifier = Modifier,
    singleEventManager: SingleEventManager = SingleEventManager(),
    enabled: Boolean = true,
    label: String,
    notSetLabel: String? = null,
    items: List<T>,
    selectedIndex: Int = -1,
    onItemSelected: (index: Int, item: T) -> Unit,
    selectedItemToStartString: (T) -> String = { it.toString() },
    withEndText: Boolean = false,
    selectedItemToEndString: (T) -> String = { it.toString() },
    withFieldText: Boolean = false,
    selectedItemToFieldString: (T) -> String = { it.toString() },
    drawItem: @Composable (String, String, Boolean, Boolean, () -> Unit) -> Unit = { itemNameStart, itemNameEnd, selected, itemEnabled, onClick ->
        LargeDropdownMenuItem(
            textStart = itemNameStart,
            textEnd = itemNameEnd,
            selected = selected,
            enabled = itemEnabled,
            onClick = onClick,
        )
    },
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        OutlinedTextField(
            label = { Text(label) },
            value = items.getOrNull(selectedIndex)?.let(block = {
                if (withFieldText) selectedItemToFieldString(it)
                else selectedItemToStartString(it)
            }) ?: "",
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                val icon =
                    if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
                Icon(icon, "")
            },
            onValueChange = { },
            readOnly = true,
        )

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .clip(MaterialTheme.shapes.extraSmall)
//                .clickable(enabled = enabled) { expanded = true }
                .onSingleClick(
                    singleEventManager = singleEventManager,
                    onClick = { if (enabled) expanded = true  }
                )
            ,
            color = Color.Transparent,
        ) {}
    }

    if (expanded) {
        Dialog(
            onDismissRequest = { expanded = false },
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
            ) {
                val listState = rememberLazyListState()
                if (selectedIndex > -1) {
                    LaunchedEffect("ScrollToSelected") {
                        listState.scrollToItem(index = selectedIndex)
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                    if (notSetLabel != null) {
                        item {
                            LargeDropdownMenuItem(
                                textStart = notSetLabel,
                                selected = false,
                                enabled = false,
                                onClick = { },
                            )
                        }
                    }
                    itemsIndexed(items) { index, item ->
                        val selectedItem = index == selectedIndex
                        val nameItemStart = selectedItemToStartString(item)
                        val nameItemEnd = if (withEndText) selectedItemToEndString(item) else ""
                        drawItem(
                            nameItemStart,
                            nameItemEnd,
                            selectedItem,
                            true
                        ) {
                            onItemSelected(index, item)
                            expanded = false
                        }

                        if (index < items.lastIndex) {
                            Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun LargeDropdownMenuItem(
    textStart: String,
    textEnd: String = "",
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val contentColor = when {
        !enabled -> MaterialTheme.colorScheme.onSurface
        selected -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurface
    }

    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .clickable(enabled = enabled, onClick = onClick)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = textStart,
                style = MaterialTheme.typography.titleSmall,
            )
            if (textEnd.isNotEmpty()) {
                Text(
                    text = textEnd,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}
