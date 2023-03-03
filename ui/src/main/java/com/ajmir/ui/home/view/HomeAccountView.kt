package com.ajmir.ui.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ajmir.ui.commons.resources.Colors
import com.ajmir.ui.commons.resources.Dimens
import com.ajmir.ui.commons.resources.RoundedShape
import com.ajmir.ui.home.model.HomeAccountState

@Composable
fun HomeAccountView(
    accounts: List<HomeAccountState>,
    onAccountClicked: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(all = Dimens.Spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.medium),
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.backgroundSecondary)
    ) {
        items(accounts) {
            HomeAccountItem(
                account = it,
                onClick = { onAccountClicked(it.id) }
            )
        }
    }
}

@Composable
fun HomeAccountItem(
    account: HomeAccountState,
    onClick: () -> Unit
) {
    Box(modifier = Modifier
        .alpha(if (account.isSelected) 1f else 0.7f)
        .background(Colors.accountBrushBackground, RoundedShape)
        .size(100.dp)
        .clickable(onClick = onClick)
    ) {
        Text(
            text = account.name.replace(" ", "\n"),
            color = Color.White,
            fontSize = Dimens.FontSize.h3,
            modifier = Modifier.padding(Dimens.Spacing.small)
        )
    }
}
