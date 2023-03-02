package com.ajmir.ui.home.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ajmir.ui.commons.resources.Colors
import com.ajmir.ui.commons.resources.Dimens
import com.ajmir.ui.commons.resources.RoundedShape
import com.ajmir.ui.home.model.HomeAccount

@Composable
fun HomeAccountView(
    accounts: List<HomeAccount>,
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
                onClick = {
                    Log.e("test", "HomeAccountView: onclick ", )
                    onAccountClicked(it.id)
                }
            )
        }
    }
}

@Composable
fun HomeAccountItem(
    account: HomeAccount,
    onClick: () -> Unit
) {
    Box(modifier = Modifier
        .background(Colors.primary, RoundedShape)
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