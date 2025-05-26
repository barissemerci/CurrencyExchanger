package com.barissemerci.currencyexchanger.core.presentation.designsystem.dropdowns

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.CurrencyExchangerTheme

@Composable
fun DropDownOptionsMenu(
    items: List<Selectable>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    dropdownOffset: IntOffset = IntOffset.Zero,
    maxDropDownHeight: Dp = Dp.Unspecified,
) {
    Popup(
        onDismissRequest = onDismiss,
        offset = dropdownOffset
    ) {
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(10.dp),
            shadowElevation = 4.dp,
            modifier = modifier
                .heightIn(max = maxDropDownHeight)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .animateContentSize()
                    .padding(6.dp)
            ) {
                items(items = items) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (it.isSelected) Color.LightGray else Color.Transparent,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable {
                                onItemClick(it.item)
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = it.item)
                        if (it.isSelected) {
                            Text("✓")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DropDownOptionsMenuPreview() {
    CurrencyExchangerTheme {
        DropDownOptionsMenu(
            items = listOf(
                Selectable(
                    "Option 1",
                    isSelected = true,
                ),
                Selectable(
                    "Option 1",
                    isSelected = false,
                ),
                Selectable(
                    "Option 1",
                    isSelected = false,
                )

            ),
            onDismiss = {},
            onItemClick = {},
            maxDropDownHeight = 500.dp,
        )
    }
}