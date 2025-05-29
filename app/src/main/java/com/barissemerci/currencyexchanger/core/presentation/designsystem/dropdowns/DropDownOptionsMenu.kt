package com.barissemerci.currencyexchanger.core.presentation.designsystem.dropdowns

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.CurrencyExchangerTheme

@Composable
fun DropDownOptionsMenu(
    items: List<Selectable>,
    isExpanded: Boolean,
    onClick: () -> Unit,
    selectedItem: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
    maxDropDownHeight: Dp = Dp.Unspecified,
) {

    Column(
        horizontalAlignment = Alignment.End
    ) {
        Text(text = selectedItem, fontSize = 24.sp, modifier = Modifier.clickable {
            onClick()
        })

        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + slideInVertically(
                initialOffsetY = { -it }
            ),
        ) {
            Popup(
                alignment = Alignment.TopEnd,
                onDismissRequest =
                    {
                        onDismiss()
                    }
            ) {
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp),
                    shadowElevation = 4.dp,
                    modifier = modifier
                        .heightIn(max = maxDropDownHeight)

                        .padding(10.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .animateContentSize()
                            .padding(6.dp)
                    ) {
                        itemsIndexed(items = items) { index, item ->


                            Row(
                                modifier = Modifier
                                    .width(100.dp)
                                    .background(
                                        if (item.isSelected) Color.LightGray else Color.Transparent,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .clickable {
                                        onItemClick(index)
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = item.currency)
                                if (item.isSelected) {
                                    Text("✓")
                                }
                            }
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
            isExpanded = true,
            selectedItem =
                "Option 1",

            onClick = {},
            maxDropDownHeight = 500.dp,
        )
    }
}